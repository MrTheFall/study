package org.lab6.commands;

import common.network.Request;
import common.network.Response;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

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
    public Response apply(Request request) {
        try {
            return new Response(true, null, collectionManager.getCollection());
        } catch (Exception e) {
            return new Response(false, e.toString(), null);
        }
    }
}
