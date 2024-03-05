package org.lab6.commands;

import common.network.requests.AddIfMaxRequest;
import common.network.requests.Request;
import common.network.responses.AddIfMaxResponse;
import common.network.responses.Response;
import org.lab6.managers.CollectionManager;
import common.models.Dragon;
import org.lab6.utils.Ask;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 */
public class AddIfMax extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response apply(Request request) {
        try {
            var req = (AddIfMaxRequest) request;
            int maxAge = maxAge();
            if (maxAge == -1 || req.dragon.getAge() > maxAge) {
                req.dragon.setId(collectionManager.getFreeId());
                collectionManager.add(req.dragon);
                console.println("Дракон успешно добавлен!");
            } else {
                console.println("Дракон не добавлен, его возрасть не превышает возраст самого старого дракона (" + req.dragon.getAge() + " < " + maxAge + ")");
                return new AddIfMaxResponse(false, "Дракон не добавлен, его возрасть не превышает возраст самого старого дракона (" + req.dragon.getAge() + " < " + maxAge + ")");
            }
            return new AddIfMaxResponse(true, null);
        } catch (Exception e) {
            return new AddIfMaxResponse(false, "Дракон не был добавлен...");
        }
    }

    private int maxAge() {
        return collectionManager.getCollection().stream()
                .map(Dragon::getAge)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1);
    }
}
