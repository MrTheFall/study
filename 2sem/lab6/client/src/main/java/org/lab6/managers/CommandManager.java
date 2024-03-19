package org.lab6.managers;


import common.network.Request;
import common.network.Response;
import common.utils.Command;
import common.utils.CommandDTO;
import org.lab6.commands.GenericCommand;
import org.lab6.network.TCPClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Менеджер команд.
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final List<String> commandHistory = new ArrayList<>();

    /**
     * Добавляет команду.
     * @param commandName Название команды.
     * @param command Команда.
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * @return Словарь команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    public void updateCommands(TCPClient client) throws IOException, ClassNotFoundException {
        Response response = client.sendAndReceiveCommand(new Request(new CommandDTO("update_commands", null, null), null));

        ArrayList<Command> serverCommands = response.getCommands();
        if (serverCommands == null) {
            return;
        }

        serverCommands.forEach((n) -> commands.put(n.getName(), new GenericCommand(n.getName(), n.getDescription(), n.getArgumentType())));
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
