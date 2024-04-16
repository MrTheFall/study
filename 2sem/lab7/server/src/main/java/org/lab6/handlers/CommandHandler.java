package org.lab6.handlers;

import common.models.Dragon;
import common.models.User;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.AccessType;
import org.lab6.commands.Register;
import org.lab6.managers.CommandManager;
import org.lab6.managers.DatabaseManager;

import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CommandHandler {
    public final CommandManager manager;
    private final DatabaseManager databaseManager;
    private final ReadWriteLock lock;

    public CommandHandler(CommandManager manager, DatabaseManager databaseManager) {
        this.manager = manager;
        this.databaseManager = databaseManager;
        this.lock = new ReentrantReadWriteLock();
    }

    public Response handle(Request request) {
        var command = manager.getCommands().get(request.getCommand().getName());
        if (command == null) return new Response(false, "No such command: "+ request.getCommand(), (Vector<Dragon>) null);
        if (command.getArgumentType().contains(ArgumentType.AUTH_SESSION)) {
            var user = (User) request.getArguments().get(ArgumentType.AUTH_SESSION);
            if (user != null){
                if (databaseManager.getUser(user) == null) {
                    return new Response(false, "Session is incorrect!", (User) null);
                }
            }
            else {
                return new Response(false, "No session was sent!");
            }
        }
        AccessType accessType = command.getAccessType();
        Response response;
        try {
            switch (accessType) {
                case READ:
                    lock.readLock().lock();
                    break;
                case WRITE:
                case READ_WRITE:
                    lock.writeLock().lock();
                    break;
                case NONE:
                default:
                    break;
            }
            response = command.apply(request.getArguments());
        } finally {
            switch (accessType) {
                case READ:
                    lock.readLock().unlock();
                    break;
                case WRITE:
                case READ_WRITE:
                    lock.writeLock().unlock();
                    break;
                case NONE:
                default:
                    break;
            }
        }
        return response;
    }
}