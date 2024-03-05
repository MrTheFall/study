package org.lab6.commands;

import common.network.requests.RemoveByIdRequest;
import common.network.requests.Request;
import common.network.responses.RemoveByIdResponse;
import common.network.responses.Response;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по ID.
 */
public class RemoveById extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (RemoveByIdRequest) request;
        if (collectionManager.getById(req.id) == null || !collectionManager.getCollection().contains(collectionManager.getById(req.id))) {
            console.println("не существующий ID");
            return new RemoveByIdResponse(false, "не существующий ID");
        }
        collectionManager.remove(req.id);
        collectionManager.update();
        console.println("Дракон успешно удалён!");
        return new RemoveByIdResponse(true, null);
    }
}
