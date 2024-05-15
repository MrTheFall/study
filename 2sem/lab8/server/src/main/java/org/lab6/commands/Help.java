package org.lab6.commands;

import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;
import common.utils.CommandDTO;
import org.lab6.managers.CommandManager;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
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
    public Response apply(Map<ArgumentType, Object> args) {
        try {
            ArrayList<Command> commandList = new ArrayList<>();
            for (Map.Entry<String, Command> entry : commandManager.getCommands().entrySet()) {
                    commandList.add(new CommandDTO(entry.getValue().getName(), entry.getValue().getDescription(), entry.getValue().getArgumentType()));
            }
            return new Response(true, "Справка по командам:", commandList);
        } catch (Exception e ) {
            console.println("Не удалось вывести справку:" + e);
            return new Response(false, "Не удалось вывести справку");
        }
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>();
    }
    public AccessType getAccessType(){
        return AccessType.NONE;
    }
}
