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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Register extends Command implements Serializable {
    private static final long serialVersionUID = 302740660234595209L;
    private final int MAX_USERNAME_LENGTH = 40;
    private final DatabaseManager databaseManager;

    public Register(Console console, DatabaseManager databaseManager) {
        super("register", "зарегистрировать пользователя");
        this.databaseManager = databaseManager;
    }

    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        var user = (User) args.get(ArgumentType.AUTH_SESSION);
        if (databaseManager.getUser(user) == null) {
            if ((user).getUsername().length() >= MAX_USERNAME_LENGTH) {
                return new Response(false, "Имя пользователя слишком длинное.!");
            }
            databaseManager.addUser(user);
            return new Response(true, "Пользователь " + user.getUsername() + " успешно зарегистрирован!", user);
        }
        return new Response(false, "Пользователь не был зарегистрирован!");
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.AUTH_REQUEST));
    }

    public AccessType getAccessType(){
        return AccessType.NONE;
    }
}