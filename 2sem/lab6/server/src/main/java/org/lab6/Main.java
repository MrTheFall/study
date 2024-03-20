package org.lab6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lab6.commands.*;
import org.lab6.handlers.CommandHandler;
import org.lab6.managers.CollectionManager;
import org.lab6.managers.CommandManager;
import org.lab6.managers.DumpManager;
import org.lab6.managers.TCPServer;
import org.lab6.utils.Runner;
import org.lab6.utils.console.StandardConsole;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    public static final int PORT = 25565;

    public static Logger logger = LogManager.getLogger("ServerLogger");

    public static void main(String[] args) {
        var console = new StandardConsole();

        String filename = System.getenv("FILENAME");
        if (filename == null || filename.isEmpty()) {
            System.out.println("Задайте имя загружаемого файла с помощью переменной среды FILENAME");
            System.exit(1);
        }
        System.out.println(filename);
        var dumpManager = new DumpManager(filename, console);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.init()) { System.exit(1); }

        collectionManager.validateAll(console);
        Runtime.getRuntime().addShutdownHook(new Thread(collectionManager::saveCollection));
        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add_if_max", new AddIfMax(console, collectionManager));
            register("insert", new Insert(console, collectionManager));
            register("sort", new Sort(console, collectionManager));
            register("average_of_age", new AverageOfAge(console, collectionManager));
            register("group_counting_by_creation_date", new GroupCountingByCreationDate(console, collectionManager));
            register("print_unique_color", new PrintUniqueColor(console, collectionManager));
        }};

        Thread interactiveModeThread = new Thread(() -> {
            try {
                new Runner(console, collectionManager).interactiveMode();
            } catch (Exception e) {
                logger.error("Ошибка при создании интерактивного режима Runner", e);
            }
        });

        interactiveModeThread.start();

        Thread serverThread = new Thread(() -> {
            try {
                var server = new TCPServer(PORT, new CommandHandler(commandManager));
                //server.setAfterHook(collectionManager::saveCollection); // Уберем если не хотим сохранять коллекцию после каждой команды
                server.run();
            } catch (SocketException e) {
                logger.fatal("Случилась ошибка сокета", e);
            } catch (UnknownHostException e) {
                logger.fatal("Неизвестный хост", e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        serverThread.start();

        try {
            interactiveModeThread.join();
            serverThread.join();
        } catch (InterruptedException e) {
            logger.error("Main thread was interrupted", e);
        }

    }
}