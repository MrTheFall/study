package org.lab6.commands;

import java.time.LocalDateTime;

import common.network.requests.InfoRequest;
import common.network.responses.InfoResponse;
import common.network.requests.Request;
import common.network.responses.Response;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;
/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Info(Console console, CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (InfoRequest) request;
        try {
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
            console.println("Отправлена информация о коллекции!");
            return new InfoResponse(collectionManager.collectionType(), collectionManager.collectionSize(), lastSaveTimeString, lastInitTimeString, null);
        } catch (Exception e) {
            console.println("Ошибка при отправке информации о коллекции!");
            return new InfoResponse(null, 0,null,null, "Ошибка при отправке информации о коллекции");
        }
    }
}