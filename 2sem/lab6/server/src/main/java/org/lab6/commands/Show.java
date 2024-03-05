package org.lab6.commands;

import common.network.requests.Request;
import common.network.requests.ShowRequest;
import common.network.responses.ShowResponse;
import common.network.responses.Response;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.ExecutionResponse;
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
        var req = (ShowRequest) request;
        try {
            return new ShowResponse(collectionManager.getCollection(), null);
        } catch (Exception e) {
            return new ShowResponse(null, e.toString());
        }
    }
}
