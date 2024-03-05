package org.lab6.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import common.network.responses.Response;
import org.lab6.exceptions.ScriptRecursionException;
import org.lab6.managers.CommandManager;
import org.lab6.utils.console.Console;

/**
 * Класс для выполнения команд в различных режимах.
 */
public class Runner {
    private Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();
    private int lengthRecursion = -1;

    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Launchs the command.
     * @param userCommand Команда для запуска
     * @return Код завершения.
     */
//    private Response launchCommand(String[] userCommand) {
//        if (userCommand[0].equals("")) return new ExecutionResponse(true, "OK");
//        var command = commandManager.getCommands().get(userCommand[0]);
//
//        if (command == null || userCommand[0].equals("save") || userCommand[0].equals("load"))
//            return new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");//error
//
//        var resp = command.apply(userCommand);
//        if (resp == null) return new ExecutionResponse(false, "503");
//        return resp;
//    }

//    public Object executeCommand(String s, Object obj) {
//        try {
//            commandManager.getCommands().get("load").apply(new String[]{"load", ""});
//            String[] userCommand = {"", ""};
//            userCommand = (s.replace('\n',' ').replace('\r',' ') + " ").split(" ", 2);
//            userCommand[1] = userCommand[1].trim();
//            System.out.println("$ "+userCommand[0]);
//
//            commandManager.addToHistory(userCommand[0]);
//
//            return launchCommand(userCommand);
//        } finally {
//            commandManager.getCommands().get("save").apply(new String[]{"save", ""});
//        }
//    }

}