package org.lab6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lab6.commands.*;
import org.lab6.handlers.CommandHandler;
import org.lab6.managers.*;
import org.lab6.utils.console.StandardConsole;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class Main {
    public static final int PORT = 25565;

    public static Logger logger = LogManager.getLogger("ServerLogger");

    public static void main(String[] args) throws SQLException {
        var console = new StandardConsole();

//        String filename = System.getenv("FILENAME");
//        if (filename == null || filename.isEmpty()) {
//            System.out.println("Задайте имя загружаемого файла с помощью переменной среды FILENAME");
//            System.exit(1);
//        }
//        System.out.println(filename);
        //var dumpManager = new DumpManager(filename, console);
        var dbUrl = System.getenv("PG_URL");
        var dbUsername = System.getenv("PG_USERNAME");
        var dbPassword = System.getenv("PG_PASSWORD");
        var databaseManager = new DatabaseManager(dbUrl, dbUsername, dbPassword);
        var collectionManager = new CollectionManager(databaseManager);
        if (!collectionManager.init()) { System.exit(1); }

        collectionManager.validateAll(console);
        //Runtime.getRuntime().addShutdownHook(new Thread(collectionManager::saveCollection));
        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("add", new Add(console, collectionManager, databaseManager));
            register("update", new Update(console, collectionManager, databaseManager));
            register("remove_by_id", new RemoveById(console, collectionManager, databaseManager));
            register("clear", new Clear(console, collectionManager, databaseManager));
            register("show", new Show(console, collectionManager));
            register("add_if_max", new AddIfMax(console, collectionManager, databaseManager));
            register("sort", new Sort(console, collectionManager));
            register("average_of_age", new AverageOfAge(console, collectionManager));
            register("group_counting_by_creation_date", new GroupCountingByCreationDate(console, collectionManager));
            register("print_unique_color", new PrintUniqueColor(console, collectionManager));
            register("register", new Register(console, databaseManager));
            register("login", new Login(console, databaseManager));

            register("exit", new Exit(console));                        // Server-only command
            register("save", new Save(console, collectionManager));     // Server-only command
        }};

        try {
            var server = new TCPServer(PORT, new CommandHandler(commandManager, databaseManager));
            //server.setAfterHook(collectionManager::saveCollection);
            server.run();
        } catch (SocketException e) {
            logger.fatal("Случилась ошибка сокета", e);
        } catch (UnknownHostException e) {
            logger.fatal("Неизвестный хост", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}