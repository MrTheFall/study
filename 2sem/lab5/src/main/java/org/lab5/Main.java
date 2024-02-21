package org.lab5;

import org.lab5.commands.*;
import org.lab5.managers.CollectionManager;
import org.lab5.managers.CommandManager;
import org.lab5.managers.DumpManager;
import org.lab5.utils.Runner;
import org.lab5.utils.console.StandardConsole;

public class Main {
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

        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("add_if_max", new AddIfMax(console, collectionManager));
            register("insert", new Insert(console, collectionManager));
            register("sort", new Sort(console, collectionManager));
            register("average_of_age", new AverageOfAge(console, collectionManager));
            register("group_counting_by_creation_date", new GroupCountingByCreationDate(console, collectionManager));
            register("print_unique_color", new PrintUniqueColor(console, collectionManager));
        }};

        new Runner(console, commandManager).interactiveMode();
    }
}