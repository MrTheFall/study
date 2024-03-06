package org.lab6.network;

import org.lab6.Main;
import common.network.Response;
import common.network.Request;

import org.apache.logging.log4j.Logger;
import static org.apache.logging.log4j.LogManager.getLogger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class TCPClient {

    private final SocketChannel client;
    private final InetSocketAddress serverAddress;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private final Logger logger = getLogger(Main.class); // This is an example of how you would typically get the logger via LogManager

    public TCPClient(InetAddress hostname, int port) throws IOException {
        this.serverAddress = new InetSocketAddress(hostname, port);
        this.client = SocketChannel.open();
        this.client.connect(serverAddress);

        // Prepare the streams
        outputStream = new ObjectOutputStream(Channels.newOutputStream(client));
        inputStream = new ObjectInputStream(Channels.newInputStream(client));

        logger.info("SocketChannel opened connection to " + serverAddress);
    }

    public Response sendAndReceiveCommand(Request request) throws IOException, ClassNotFoundException {
        outputStream.writeObject(request);
        outputStream.flush();

        Response response = (Response)inputStream.readObject();

        logger.info("Received response from server: " + response);
        return response;
    }

    public void close() throws IOException {
        if (outputStream != null) {
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        if (client != null) {
            client.close();
        }
        logger.info("Connection to server " + serverAddress + " closed.");
    }
}
