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
            register("execute_script", new ExecuteScript(console)); // Это клиентские команды для клиентского приложения
            register("exit", new Exit(console));
        }};
        this.commandManager.updateCommands(client);
        this.responseHandler = new ResponseHandler();
        //console.println(this.commandManager.getCommands());
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
        } catch (Ask.AskBreak | ClassNotFoundException | IOException | APIException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Режим для запуска скрипта.
     * @param argument Аргумент скрипта
     * @return Код завершения.
     */
    public ExitCode scriptMode(String argument) {
        String[] userCommand = {"", ""};
        ExitCode commandStatus;
        scriptStack.add(argument);
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);

            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                console.println(console.getPrompt() + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script))
                        {
                            console.selectConsoleScanner();
                            throw new ScriptRecursionException();
                        }
                    }
                }
                commandStatus = launchCommand(userCommand);
            } while (commandStatus == ExitCode.OK && scriptScanner.hasNextLine());

            console.selectConsoleScanner();
            if (commandStatus == ExitCode.ERROR && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                console.println("Проверьте скрипт на корректность введенных данных!");
            }

            return commandStatus;

        } catch (FileNotFoundException exception) {
            console.printError("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            console.printError("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            console.printError("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } catch (Ask.AskBreak | APIException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return ExitCode.ERROR;
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
    private ExitCode launchCommand(String[] userCommand) throws Ask.AskBreak, APIException, IOException, ClassNotFoundException {
        if (userCommand[0].isEmpty()) return ExitCode.OK;
        Command command = commandManager.getCommands().get(userCommand[0]);
        if (command == null) {
            console.printError("Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
            return ExitCode.ERROR;
        }
        CommandDTO commandDTO = new CommandDTO(command.getName(), command.getDescription(), command.getArgumentType());

        switch (userCommand[0]) {
            case "exit" -> {
                if (!commandManager.getCommands().get("exit").apply(new HashMap<>()).isSuccess()) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            case "execute_script" -> {
                if (!commandManager.getCommands().get("execute_script").apply(new HashMap<>()).isSuccess()) return ExitCode.ERROR;
                else return scriptMode(userCommand[1]);
            }
            default -> {
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
            }
        };

        return ExitCode.OK;
    }
}