package org.lab6.commands;

import common.network.requests.AddRequest;
import common.network.requests.Request;
import common.network.responses.AddResponse;
import common.network.responses.Response;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console            интерфейс консоли
     * @param collectionManager  менеджер коллекции
     */
    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Ответ после выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (AddRequest) request;
        try {
            if (req.dragon == null || !req.dragon.validate()) {
                return new AddResponse(false, "Поля дракона не валидны! Дракон не создан!");
            }
            req.dragon.setId(collectionManager.getFreeId());
            boolean success = collectionManager.add(req.dragon);
            console.println("Дракон успешно добавлен!");
            return new AddResponse(success, null);
        } catch (Exception e) {
            console.printError(e.toString());
            return new AddResponse(false, e.toString());
        }
    }
}






