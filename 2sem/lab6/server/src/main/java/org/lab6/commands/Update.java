package org.lab6.commands;

import common.network.requests.Request;
import common.network.requests.UpdateRequest;
import common.network.responses.Response;
import common.network.responses.UpdateResponse;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

/**
 * Команда 'update'. Обновляет значение элемента коллекции по ID.
 */
public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public Update(Console console, CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
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
            var req = (UpdateRequest) request;
            int id = req.id;
            var old = collectionManager.getById(id);
            if (old == null || !collectionManager.getCollection().contains(old)) {
                console.println("не существующий ID");
                return new UpdateResponse(false, "не существующий ID!");
            }
            var d = req.dragon;
            d.setId(id);
            if (d != null && d.validate()) {
                collectionManager.remove(old.getId());
                collectionManager.add(d);
                collectionManager.update();
                return new UpdateResponse(true, null);
            } else {
                console.println("Поля Дракона не валидны! Дракон не создан!");
                return new UpdateResponse(false, "Поля Дракона не валидны! Дракон не обновлен!");
            }
        } catch (Exception e) {
            console.printError(e.toString());
            return new UpdateResponse(false, e.toString());
        }
    }
}
