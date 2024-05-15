package org.lab6.managers;


import common.utils.ArgumentType;
import common.utils.Command;
import common.utils.CommandDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Менеджер команд.
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final ArrayList<Command> commandsList = new ArrayList<>();
    private final List<String> commandHistory = new ArrayList<>();

    /**
     * Добавляет команду.
     * @param commandName Название команды.
     * @param command Команда.
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
        commandsList.add(command);
    }

    /**
     * @return Словарь команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    public ArrayList<Command> getCommandsList() {
        return commandsList;
    }

    public ArrayList<Command> getCommandsWithArguments() {
        Map<String, Command> commands = this.getCommands();
        ArrayList<Command> commandsWithArguments = new ArrayList<>();

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            commandsWithArguments.add(new CommandDTO(entry.getKey(), entry.getValue().getDescription(), entry.getValue().getArgumentType()));
        }
        return commandsWithArguments;
    }

    /**
     * @return История команд.
     */
    public List<String> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Добавляет команду в историю.
     * @param command Команда.
     */
    public void addToHistory(String command) {
        commandHistory.add(command);
    }
}
