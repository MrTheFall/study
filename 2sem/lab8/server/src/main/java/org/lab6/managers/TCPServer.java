package org.lab6.managers;

import common.models.Dragon;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.CommandDTO;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.Logger;
import org.lab6.Main;
import org.lab6.handlers.CommandHandler;
import org.lab6.utils.Runner;
import org.lab6.utils.console.Console;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TCP обработчик запросов
 */
public class TCPServer {
    private final Selector selector;
    private final ServerSocketChannel serverSocket;
    private final CommandHandler commandHandler;
    private final Logger logger = Main.logger;
    private final ByteBuffer buffer;
    private boolean running = true;

    // Create two different CachedThreadPool for handling and sending response
    private final ExecutorService handlePool = Executors.newCachedThreadPool();
    private final ExecutorService sendPool = Executors.newCachedThreadPool();

    public TCPServer(int port, CommandHandler commandHandler) throws IOException {
        this.buffer = ByteBuffer.allocate(32000);
        this.selector = Selector.open();
        this.serverSocket = ServerSocketChannel.open();
        this.serverSocket.bind(new InetSocketAddress(port));
        this.serverSocket.configureBlocking(false);
        this.serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        this.commandHandler = commandHandler;
    }
    public void run() throws IOException {
        logger.info("TCP Server started on port " + serverSocket.socket().getLocalPort());

        while (running) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();

            while (iter.hasNext()) {
                synchronized (iter) {
                    SelectionKey key = iter.next();
                    if (key.isValid() && key.isAcceptable()) {
                        register(selector);
                    }

                    if (key.isValid() && key.isReadable()) {
                        new Thread(() -> {
                            answer(selector, key);
                        }).start();
                    }

                    iter.remove();
                }
                if (System.in.available() > 0) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    String[] line= (bufferedReader.readLine().trim() + " ").split(" ", 2);
                    Request request = new Request(new CommandDTO(line[0], "", new ArrayList<>()), new HashMap<>());
                    commandHandler.handle(request);
                }
            }
        }
    }

    private void register(Selector selector) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        SelectionKey key = client.register(selector, SelectionKey.OP_READ);
    }



    private Request read(SocketChannel client) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        buffer.clear();
        int r = 0;
        try {
            while ((r = client.read(buffer)) > 0) {
                buffer.flip();
                baos.write(buffer.array(), 0, r);
                buffer.clear();
            }
        } catch (SocketException | RuntimeException | ClosedChannelException se) {
            logger.error("Connection reset by client");
            client.close();
            return null;
        }

        if (r == -1) {
            client.close();
            return null;
        }

        byte[] bytes = baos.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            Request request = (Request) ois.readObject();
            logger.info("Processing request: " + request);

            return request;
        } catch (EOFException e) {
            //System.out.println("Incomplete object received");
            return null;
        }
    }
    private void write(SocketChannel client, Response response) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(response);
        oos.flush();

        buffer.clear();
        buffer.put(bos.toByteArray());
        buffer.flip();
        while (buffer.hasRemaining()) {
            client.write(buffer);
        }
    }

    private Response handleRequest(Request request) {
        Response response;
        if (request.getCommand().getName().equals("update_commands")) {
            response = new Response(true, null, commandHandler.manager.getCommandsWithArguments());
        } else {
            response = commandHandler.handle(request);
        }
        return response;
    }

    private void answer(Selector selector, SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();

        if (!client.isOpen()) {
            return;
        }

        try {
            Request request = read(client);
            if(request == null) return;

            // Use handlePool to handle the request
            handlePool.execute(() -> {
                Response response = handleRequest(request);

                // Use sendPool to send the response
                sendPool.execute(() -> {
                    try {
                        if (client.isOpen()) {
                            write(client, response);
                        }
                        else System.out.println("NOT OPEN");
                    } catch (RuntimeException | IOException e) {
                        logger.error("Failed to write response");
                        try {
                            client.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            });
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Client disconnected", e);
            try {
                client.close();
            } catch (IOException ex) {
                logger.error("Failed to close client", ex);
            }
        }
    }


    public void stop() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("Failed to close server socket when stopping: " + e.getMessage(), e);
        }
    }
}