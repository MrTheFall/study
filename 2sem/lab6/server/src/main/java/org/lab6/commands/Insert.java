package org.lab6.commands;

import common.network.requests.InsertRequest;
import common.network.requests.Request;
import common.network.responses.InsertResponse;
import common.network.responses.Response;
import common.network.responses.UpdateResponse;
import org.lab6.managers.CollectionManager;
import org.lab6.models.Dragon;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;
import org.lab6.utils.Ask;

/**
 * Команда 'insert'. Вставляет новый элемент в заданное место коллекции.
 */
public class Insert extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public Insert(Console console, CollectionManager collectionManager) {
        super("insert <ID> {element}", "вставить новый элемент в заданное место коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response apply(Request request) {
        var req = (InsertRequest) request;
        try {
            int insertIndex = req.insertIndex;
            if (insertIndex < 0 ){
                insertIndex = 0;
            }
            if (insertIndex > collectionManager.getCollection().size()){
                insertIndex = collectionManager.getCollection().size();
            }
            req.dragon.setId(collectionManager.getFreeId());
            if (req.dragon != null && req.dragon.validate()) {
                collectionManager.insert(req.dragon, insertIndex);
                console.println("Дракон по индексу " + insertIndex + " успешно добавлен!");
                return new InsertResponse(true, null);
            } else {
                console.printError("Поля дракона не валидны! Дракон не добавлен.");
                return new InsertResponse(false, "Поля дракона не валидны! Дракон не добавлен.");
            }
        } catch (Exception e) {
            console.printError(e.toString());
            return new InsertResponse(false, e.toString());
        }
    }
}
