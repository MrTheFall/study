package org.lab6.commands;

import common.network.requests.Request;
import common.network.responses.Response;
import common.network.responses.SortResponse;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

/**
 * Команда 'sort'. Сортирует коллекцию в естественном порядке.
 */
public class Sort extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public Sort(Console console, CollectionManager collectionManager) {
        super("sort", "сортировка коллекции в естественном порядке");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду сортировки.
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response apply(Request request) {
        try {
            console.println("* Сортировка коллекции:");
            collectionManager.sort();
            console.println("Коллекция успешно отсортирована!");
            return new SortResponse(true, null);
        } catch (Exception e) {
            console.printError("Возникла ошибка при попытке сортировки коллекции: " + e.getMessage());
            return new SortResponse(false, "Возникла ошибка при попытке сортировки коллекции: " + e.getMessage());
        }
    }
}
