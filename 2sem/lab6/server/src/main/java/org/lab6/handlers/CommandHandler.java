package org.lab6.handlers;

import common.network.Request;
import common.network.Response;
import org.lab6.managers.CommandManager;

public class CommandHandler {
    private final CommandManager manager;

    public CommandHandler(CommandManager manager) {
        this.manager = manager;
    }

    public Response handle(Request request) {
        var command = manager.getCommands().get(request.getCommand());
        if (command == null) return new Response(false, "No such command: "+ request.getCommand(), null);
        return command.apply(request);
    }
}