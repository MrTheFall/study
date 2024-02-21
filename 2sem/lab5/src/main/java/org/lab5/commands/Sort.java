package org.lab5.commands;

import org.lab5.managers.CollectionManager;
import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;

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
    public ExecutionResponse apply(String[] arguments) {
        try {
            console.println("* Сортировка коллекции:");
            collectionManager.sort();
            console.println("Коллекция успешно отсортирована!");
            return new ExecutionResponse(true, "Коллекция успешно отсортирована!");
        } catch (Exception e) {
            console.printError("Возникла ошибка при попытке сортировки коллекции: " + e.getMessage());
            return new ExecutionResponse(false, "Возникла ошибка при попытке сортировки коллекции: " + e.getMessage());
        }
    }
}
