package org.lab6.commands;

import common.models.Dragon;
import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 */
public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести все элементы коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        try {
            return new Response(true, null, collectionManager.getCollection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.AUTH_SESSION));
    }

    public AccessType getAccessType(){
        return AccessType.READ;
    }
}
