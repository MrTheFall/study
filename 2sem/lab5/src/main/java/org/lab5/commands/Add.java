package org.lab5.commands;

import org.lab5.managers.CollectionManager;
import org.lab5.models.Dragon;
import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;
import org.lab5.utils.Ask;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) {
                console.printError("Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
                return new ExecutionResponse(false,
                        "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            }
            console.println("* Создание нового Дракона:");
            Dragon dragon = Ask.askDragon(console, collectionManager.getFreeId());

            if (dragon != null && dragon.validate()) {
                collectionManager.add(dragon);
                console.println("Дракон успешно добавлен!");
                return new ExecutionResponse("Дракон успешно добавлен!");
            } else
                return new ExecutionResponse(false, "Поля дракона не валидны! Дракон не создан!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
        }
    }
}
