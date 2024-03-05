package org.lab6.network;

import org.lab6.Main;
import common.network.responses.Response;
import common.network.requests.Request;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPClient {
    private final SocketChannel client;
    private final InetSocketAddress serverAddress;

    private final Logger logger = Main.logger;

    public TCPClient(InetAddress hostname, int port) throws IOException {
        this.serverAddress = new InetSocketAddress(hostname, port);
        this.client = SocketChannel.open();
        this.client.connect(serverAddress);
        this.client.finishConnect();
        this.client.configureBlocking(false);

        logger.info("SocketChannel opened connection to " + serverAddress + client.isConnected());
    }

    public Response sendAndReceiveCommand(Request request) throws IOException {
        var requestData = SerializationUtils.serialize(request);
        sendData(requestData);

        var responseData = receiveData();
        Response response = SerializationUtils.deserialize(responseData);
        logger.info("Received response from server: " + response);
        return response;
    }

    private void sendData(byte[] data) throws IOException {
        // Send the size of data first
        ByteBuffer sizeBuffer = ByteBuffer.allocate(Integer.BYTES);
        sizeBuffer.putInt(data.length);
        sizeBuffer.flip();
        client.write(sizeBuffer);

        // Send the actual data
        ByteBuffer dataBuffer = ByteBuffer.wrap(data);
        while (dataBuffer.hasRemaining()) {
            client.write(dataBuffer);
        }

        logger.info("Data sent to server.");
    }

    private byte[] receiveData() throws IOException {
        // Read the size of data
        ByteBuffer sizeBuffer = ByteBuffer.allocate(Integer.BYTES);
        while (sizeBuffer.hasRemaining()) {
            client.read(sizeBuffer);
        }
        sizeBuffer.flip();
        int size = sizeBuffer.getInt();

        // Read the actual data
        ByteBuffer dataBuffer = ByteBuffer.allocate(size);
        while (dataBuffer.hasRemaining()) {
            client.read(dataBuffer);
        }
        return dataBuffer.array();
    }
}
