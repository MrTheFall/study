package org.lab6.utils;

import common.exceptions.APIException;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import common.utils.CommandDTO;
import org.lab6.commands.Exit;
import org.lab6.commands.Save;
import org.lab6.exceptions.ScriptRecursionException;
import org.lab6.managers.CollectionManager;
import org.lab6.managers.CommandManager;
import org.lab6.utils.console.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Класс для выполнения команд в различных режимах.
 */
public class Runner {

    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }

    private final Console console;
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    /**
     * Конструктор класса Runner.
     * @param console
     */
    public Runner(Console console, CollectionManager collectionManager) throws IOException, ClassNotFoundException {
        this.console = console;
        this.collectionManager = collectionManager;

        this.commandManager = new CommandManager() {{
            register("exit", new Exit(console));
            register("save", new Save(console, collectionManager));
        }};
    }

    /**
     * Интерактивный режим
     */
    public void interactiveMode() {
        try {
            ExitCode commandStatus;
            String[] userCommand = {"", ""};

            do {
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandManager.addToHistory(userCommand[0]);
                commandStatus = launchCommand(userCommand);
            } while (commandStatus != ExitCode.EXIT);

        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (RuntimeException e) {
            console.printError("Непредвиденная ошибка!");
        } catch (ClassNotFoundException | IOException | APIException e) {
            throw new RuntimeException(e);
        }
    }





    /**
     * Запускает команду.
     * @param userCommand команда для запуска.
     * @return код завершения.
     */
    private ExitCode launchCommand(String[] userCommand) throws APIException, IOException, ClassNotFoundException {
        if (userCommand[0].isEmpty()) return ExitCode.OK;
        Command command = commandManager.getCommands().get(userCommand[0]);
        if (command == null) {
            console.printError("Команда '" + userCommand[0] + "' не найдена.");
            return ExitCode.ERROR;
        }

        switch (userCommand[0]) {
            case "exit" -> {
                if (!commandManager.getCommands().get("exit").apply(new HashMap<>()).isSuccess()) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            default -> {
                try {
                    command.apply(new HashMap<>());
                } catch (IllegalArgumentException e) {
                    console.printError(e.getMessage());
                    return ExitCode.ERROR;
                }
            }
        };

        return ExitCode.OK;
    }
}