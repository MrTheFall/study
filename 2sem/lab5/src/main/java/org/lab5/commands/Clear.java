package org.lab5.commands;

import org.lab5.managers.CollectionManager;
import org.lab5.models.Dragon;
import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

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
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return new ExecutionResponse(false, "Неправильное количество аргументов!");
        }

        while (!collectionManager.getCollection().isEmpty()) {
            int lastElementIndex = collectionManager.getCollection().size() - 1;
            Dragon dragon = collectionManager.getCollection().remove(lastElementIndex);
            collectionManager.remove(dragon.getId());
        }


        collectionManager.update();
        console.println("Коллекция очищена!");
        return new ExecutionResponse(true, "Коллекция очищена!");

    }
}
