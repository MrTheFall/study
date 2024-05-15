package org.lab6.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import common.exceptions.APIException;
import common.models.Dragon;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import common.utils.CommandDTO;
import org.lab6.auth.SessionHandler;
import org.lab6.commands.*;
import org.lab6.exceptions.ScriptRecursionException;
import org.lab6.managers.CommandManager;
import org.lab6.managers.ResponseHandler;
import org.lab6.network.TCPClient;
import org.lab6.utils.console.Console;

/**
 * Класс для выполнения команд в различных режимах.
 */
public class Runner {
    private final TCPClient client;

    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }

    private final Console console;
    private final CommandManager commandManager;
    private final ResponseHandler responseHandler;
    private final List<String> scriptStack = new ArrayList<>();

    /**
     * Конструктор класса Runner.
     * @param console
     * @param client
     */
    public Runner(Console console, TCPClient client) throws IOException, ClassNotFoundException {
        this.client = client;
        this.console = console;
        this.commandManager = new CommandManager() {{
        }};
        this.commandManager.updateCommands(client);
        this.responseHandler = new ResponseHandler();
        //console.println(this.commandManager.getCommands());
    }

    public Map<ArgumentType, Object> handleArguments(ArrayList<ArgumentType> argumentTypes, String[] userCommand) throws Ask.AskBreak {
        Map<ArgumentType, Object> args = new HashMap<>();
        if (argumentTypes == null) return args;
        for (ArgumentType argumentType : argumentTypes) {
            switch (argumentType) {
                case AUTH_REQUEST:
                    try {
                        args.put(ArgumentType.AUTH_SESSION, Ask.askUser(console));
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Сессия не была получена");
                    }
                    break;
                case AUTH_SESSION:
                    try {
                        args.put(ArgumentType.AUTH_SESSION, SessionHandler.getSession());
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Сессия не была получена");
                    }
                    break;
                case ID:
                    try {
                        args.put(ArgumentType.ID, Integer.parseInt(userCommand[1]));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("ID не распознан");
                    }
                    break;
                case INDEX:
                    try {
                        args.put(ArgumentType.INDEX, Integer.parseInt(userCommand[1]));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("INDEX не распознан");
                    }
                    break;
                case DRAGON:
                    args.put(ArgumentType.DRAGON, Ask.askDragon(console));
                    break;
                case SCRIPT_NAME:
                    args.put(ArgumentType.DRAGON, userCommand[1]);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported ArgumentType: " + argumentType);
            }
        }
        return args;
    }


    /**
     * Запускает команду.
     * @param userCommand команда для запуска.
     * @return код завершения.
     */
    public ExitCode launchCommand(String[] userCommand) throws Ask.AskBreak, APIException, IOException, ClassNotFoundException {
        if (userCommand[0].isEmpty()) return ExitCode.OK;
        Command command = commandManager.getCommands().get(userCommand[0]);
        if (command == null) {
            console.printError("Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
            return ExitCode.ERROR;
        }
        CommandDTO commandDTO = new CommandDTO(command.getName(), command.getDescription(), command.getArgumentType());

        Map<ArgumentType, Object> args;
        try {
            args = handleArguments(command.getArgumentType(), userCommand);
        } catch (IllegalArgumentException e) {
            console.printError(e.getMessage());
            return ExitCode.ERROR;
        }
        var response = (Response) client.sendAndReceiveCommand(new Request(commandDTO, args));
        if (!response.isSuccess()) {
            console.printError(response.getMessage());
            return ExitCode.ERROR;
        }
        responseHandler.handle(console, response);

        return ExitCode.OK;
    }

    public TCPClient getClient() {
        return client;
    }
}