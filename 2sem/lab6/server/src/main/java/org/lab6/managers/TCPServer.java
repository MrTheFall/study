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

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

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

    public TCPServer(int port, CommandHandler commandHandler) throws IOException {
        this.buffer = ByteBuffer.allocate(4096);
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
                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    register(selector);
                }

                if (key.isReadable()) {
                    answerWithEcho(selector, key);
                }

                iter.remove();

                if (System.in.available() > 0) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    String[] line= (bufferedReader.readLine().trim() + " ").split(" ", 2);
                    Request request = new Request(new CommandDTO(line[0], "", new ArrayList<ArgumentType>()), new HashMap<>());
                    commandHandler.handle(request);
                }
            }
        }
    }

    private void register(Selector selector) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }



    private void answerWithEcho(Selector selector, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        buffer.clear();
        int r = client.read(buffer);

        if (r <= 0) {
            client.close();
            return;
        }

        buffer.flip();

        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        try {
            Request request = (Request) ois.readObject();
            logger.info("Processing request: " + request);

            Response response;
            if (request.getCommand().getName().equals("update_commands")) {
                response = new Response(true, null, commandHandler.manager.getCommandsWithArguments());
            } else if (request.getCommand().getName().equals("show")) {
                response = commandHandler.handle(request);
                Collections.sort(response.getDragons(), new Comparator<Dragon>() {
                    @Override
                    public int compare(Dragon d1, Dragon d2) {
                        int size1 = SerializationUtils.serialize(d1).length;
                        int size2 = SerializationUtils.serialize(d2).length;
                        return Integer.compare(size1, size2);
                    }
                });
            } else {
                response = commandHandler.handle(request);
            }

            oos.writeObject(response);
            oos.flush();

            buffer.clear();
            buffer.put(bos.toByteArray());
            buffer.flip();
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
        } catch (ClassNotFoundException e) {
            logger.error("Failed to read object", e);
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