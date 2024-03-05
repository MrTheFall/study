package org.lab6.managers;

import common.models.Dragon;
import common.network.requests.Request;
import common.network.responses.Response;
import common.network.responses.ShowResponse;
import org.apache.commons.lang3.SerializationException;
import org.apache.logging.log4j.Logger;

import org.apache.commons.lang3.SerializationUtils;
import org.lab6.Main;

import common.network.responses.NoSuchCommandResponse;
import org.lab6.handlers.CommandHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Comparator;

/**
 * TCP обработчик запросов
 */
public class TCPServer {
    private final ServerSocket serverSocket;
    private final CommandHandler commandHandler;
    private Runnable afterHook;

    private final Logger logger = Main.logger; // Ensure this logger is initialized properly

    private boolean running = true;

    public TCPServer(int port, CommandHandler commandHandler) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.commandHandler = commandHandler;
    }

    public void run() {
        logger.info("TCP Server started on port " + serverSocket.getLocalPort());

        while (running) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();

                logger.info("Client connected from " + clientSocket.getRemoteSocketAddress());

                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

                // Keep the connection alive to handle multiple requests
                while (running && !clientSocket.isClosed()) {
                    // Read the length of the incoming message
                    int length;
                    try {
                        length = input.readInt();
                    } catch (EOFException e) {
                        logger.info("Client disconnected gracefully.");
                        break;
                    } catch (SocketException e) {
                        logger.info("Client connection was reset.");
                        break;
                    }

                    // If length is greater than 0, process the request
                    if (length > 0) {
                        byte[] message = new byte[length];
                        input.readFully(message, 0, message.length);
                        Request request = null;
                        Response response = null;

                        // Deserialize the request and process it
                        try {
                            request = SerializationUtils.deserialize(message);
                            logger.info("Processing request: " + request);
                            response = commandHandler.handle(request);
                        } catch (SerializationException e) {
                            logger.error("Error while handling the request: " + e.toString(), e);
                            response = new NoSuchCommandResponse(request != null ? request.getName() : "unknown");
                        } finally {
                            if (afterHook != null) afterHook.run();
                        }

                        // Serialize and send the response to client
                        byte[] responseData;
                        try {
                            if (response instanceof ShowResponse showResponse) {
                                logger.error(showResponse.dragons);
                                Collections.sort(showResponse.dragons, new Comparator<Dragon>() {
                                    @Override
                                    public int compare(Dragon d1, Dragon d2) {
                                        int size1 = SerializationUtils.serialize(d1).length;
                                        int size2 = SerializationUtils.serialize(d2).length;
                                        return Integer.compare(size1, size2);
                                    }
                                });
                            }

                            responseData = SerializationUtils.serialize(response);
                            output.writeInt(responseData.length);
                            output.write(responseData);
                            output.flush();
                            logger.info("Response sent to client");
                        } catch (IOException e) {
                            logger.error("Error sending response to client: " + e.toString(), e);
                        }
                    } else {
                        logger.error("No data or incorrect data received from client.");
                    }
                }
            } catch (IOException e) {
                logger.error("Error handling client connection: " + e.toString(), e);
            } finally {
                // Disconnection moved to the while-loop break condition
                if (clientSocket != null && !clientSocket.isClosed()) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        logger.error("Failed to close client socket: " + e.toString(), e);
                    }
                }
            }
        }

        // Close server socket
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("Failed to close server socket: " + e.toString(), e);
        }
    }

    /**
     * Устанавливает хук для вызова функции после каждого запроса.
     * @param afterHook функция которая будет запущена после каждого запроса.
     */
    public void setAfterHook(Runnable afterHook) {
        this.afterHook = afterHook;
    }

    public void stop() {
        running = false; // Add flag check in while loop for proper shutdown
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("Failed to close server socket when stopping: " + e.toString(), e);
        }
    }
}

