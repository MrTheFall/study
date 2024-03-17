package org.lab6.handlers;

import common.models.Dragon;
import common.network.Request;
import common.network.Response;
import org.lab6.managers.CommandManager;

import java.util.Vector;

public class CommandHandler {
    public final CommandManager manager;

    public CommandHandler(CommandManager manager) {
        this.manager = manager;
    }

    public Response handle(Request request) {
        var command = manager.getCommands().get(request.getCommand().getName());
        if (command == null) return new Response(false, "No such command: "+ request.getCommand(), (Vector<Dragon>) null);
        return command.apply(request.getArguments());
    }
}