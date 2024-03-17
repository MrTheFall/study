package org.lab6;

import org.lab6.network.TCPClient;
import org.lab6.utils.Runner;
import org.lab6.utils.console.StandardConsole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;

public class Main {

    private static final int PORT = 25565;
    public static final Logger logger = LogManager.getLogger("ClientLogger");

    public static void main(String[] args) {
        var console = new StandardConsole();

        try {
            var client = new TCPClient(InetAddress.getLocalHost(), PORT);
            new Runner(console, client).interactiveMode();
        }catch (ConnectException e){
            logger.error("Сервер недоступен");
        } catch (IOException e) {
            logger.error(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}