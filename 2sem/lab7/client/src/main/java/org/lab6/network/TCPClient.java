package org.lab6.network;

import org.lab6.Main;
import common.network.Response;
import common.network.Request;

import org.apache.logging.log4j.Logger;
import static org.apache.logging.log4j.LogManager.getLogger;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;

public class TCPClient {

    private final SocketChannel client;
    private final InetSocketAddress serverAddress;
    private final Logger logger = getLogger(Main.class);
    private final ByteBuffer buffer;

    public TCPClient(InetAddress hostname, int port) throws IOException {
        this.buffer = ByteBuffer.allocate(16000);
        this.serverAddress = new InetSocketAddress(hostname, port);
        this.client = SocketChannel.open(this.serverAddress);

        logger.info("SocketChannel opened connection to " + serverAddress);
    }

    public Response sendAndReceiveCommand(Request request) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(request);
        oos.flush();

        buffer.clear();
        buffer.put(bos.toByteArray());
        buffer.flip();

        while (buffer.hasRemaining()) {
            client.write(buffer);
        }

        buffer.clear();
        client.read(buffer);

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Response response = (Response) ois.readObject();
        logger.info("Received response from server: " + response);
        return response;
    }

    public void close() throws IOException {
        if (client != null) {
            client.close();
        }
        logger.info("Connection to server " + serverAddress + " closed.");
    }
}