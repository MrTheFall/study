package org.lab6.commands;

import common.network.requests.ClearRequest;
import common.network.requests.Request;
import common.network.requests.ShowRequest;
import common.network.responses.ClearResponse;
import common.network.responses.Response;
import org.apache.logging.log4j.Logger;
import org.lab6.Main;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;
import common.models.Dragon;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Logger logger = Main.logger; // Ensure this logger is initialized properly

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (ClearRequest) request;
        try {
            while (!collectionManager.getCollection().isEmpty()) {
                int lastElementIndex = collectionManager.getCollection().size() - 1;
                Dragon dragon = collectionManager.getCollection().remove(lastElementIndex);
                collectionManager.remove(dragon.getId());
            }
            collectionManager.update();
            console.println("Коллекция очищена!");
            return new ClearResponse(true, null);
        } catch (Exception e) {
            console.println("Ошибка при очистке коллекции!");
            return new ClearResponse(false, "Ошибка при очистке коллекции");
        }
    }
}
