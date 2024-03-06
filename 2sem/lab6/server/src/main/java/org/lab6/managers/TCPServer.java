package org.lab6.managers;

import common.models.Dragon;
import common.network.Request;
import common.network.Response;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.Logger;
import org.lab6.Main;
import org.lab6.handlers.CommandHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Comparator;

/**
 * TCP обработчик запросов
 */
public class TCPServer {
    private final ServerSocket serverSocket;
    private final CommandHandler commandHandler;
    private Runnable afterHook;

    private final Logger logger = Main.logger;
    private boolean running = true;

    public TCPServer(int port, CommandHandler commandHandler) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.commandHandler = commandHandler;
    }

    public void run() {
        logger.info("TCP Server started on port " + serverSocket.getLocalPort());

        while (running) {
            try (Socket clientSocket = serverSocket.accept();
                 ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

                logger.info("Client connected from " + clientSocket.getRemoteSocketAddress());

                while (running && !clientSocket.isClosed()) {
                    Request request = null;
                    Response response = null;

                    try {
                        request = (Request) input.readObject();
                        logger.info("Processing request: " + request);

                        response = commandHandler.handle(request);
                        if (request.getCommand().equals("show")) {
                            logger.error(response.getDragons());
                            Collections.sort(response.getDragons(), new Comparator<Dragon>() {
                                @Override
                                public int compare(Dragon d1, Dragon d2) {
                                    int size1 = SerializationUtils.serialize(d1).length;
                                    int size2 = SerializationUtils.serialize(d2).length;
                                    return Integer.compare(size1, size2);
                                }
                            });
                        }
                    } catch (ClassNotFoundException | IOException e) {
                        logger.error("Error while receiving the request: " + e.getMessage(), e);
                        response = new Response(false, "Failed to process request: " + (request != null ? request.getCommand() : "unknown"), null);
                    } finally {
                        if (afterHook != null) afterHook.run();
                    }

                    try {
                        output.writeObject(response);
                        output.flush();
                        logger.info("Response sent to client");
                    } catch (IOException e) {
                        logger.error("Error sending response to client: " + e.getMessage(), e);
                    }
                }
            } catch (IOException e) {
                if (running) {
                    logger.error("Error handling client connection: " + e.getMessage(), e);
                }
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("Failed to close server socket: " + e.getMessage(), e);
        }
    }

    /**
     * Устанавливает хук для вызова функции после каждого запроса.
     *
     * @param afterHook функция которая будет запущена после каждого запроса.
     */
    public void setAfterHook(Runnable afterHook) {
        this.afterHook = afterHook;
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
