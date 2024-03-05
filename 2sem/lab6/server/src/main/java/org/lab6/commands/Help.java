package org.lab6.commands;

import common.network.requests.HelpRequest;
import common.network.requests.Request;
import common.network.responses.HelpResponse;
import common.network.responses.Response;
import org.lab6.managers.CommandManager;
import org.lab6.utils.console.Console;

import java.util.HashMap;
import java.util.Map;

/**
 * Команда 'help'. Выводит справку по доступным командам
 */
public class Help extends Command {
    private final Console console;
    private final CommandManager commandManager;

    /**
     * @param console
     * @param commandManager
     */
    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (HelpRequest) request;
        try {
            Map<String, String> commands = new HashMap<>();
            commandManager.getCommands().values().forEach(command -> {
                commands.put(command.getName(), command.getDescription());
            });
            return new HelpResponse(commands, null);
        } catch (Exception e ) {
            console.println("Не удалось вывести справку");
            return new HelpResponse(null, "Не удалось вывести справку");
        }
    }
}
