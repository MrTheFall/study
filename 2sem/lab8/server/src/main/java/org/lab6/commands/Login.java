package org.lab6.commands;

import common.models.User;
import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;
import org.apache.commons.lang3.ObjectUtils;
import org.lab6.managers.CollectionManager;
import org.lab6.managers.DatabaseManager;
import org.lab6.utils.console.Console;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Login extends Command {
    private static final long serialVersionUID = 955247432113473989L;

    private final DatabaseManager databaseManager;

    public Login(Console console, DatabaseManager databaseManager) {
        super("register", "зарегистрировать пользователя");
        this.databaseManager = databaseManager;
    }

    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        var user = (User) args.get(ArgumentType.AUTH_SESSION);
        if (databaseManager.getUser(user) != null) {
            return new Response(true, "Вы успешно вошли как " + user.getUsername(), user);
        }
        else {
            return new Response(false, "Такой пользователь не найден!");
        }
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.AUTH_REQUEST));
    }
    public AccessType getAccessType(){
        return AccessType.NONE;
    }

}