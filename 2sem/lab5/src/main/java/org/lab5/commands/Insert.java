package org.lab5.commands;

import org.lab5.managers.CollectionManager;
import org.lab5.models.Dragon;
import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;
import org.lab5.utils.Ask;

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
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (arguments.length < 2 || arguments[1].isEmpty()) {
                console.printError("Укажите ID для вставки элемента!\nИспользование: '" + getName() + "'");
                return new ExecutionResponse(false,
                        "Укажите ID для вставки элемента!\nИспользование: '" + getName() + "'");
            }

            int insertIndex = -1;
            try {
                insertIndex = Integer.parseInt(arguments[1].trim());
            } catch (NumberFormatException e) {
                console.printError("ID не распознан");
                return new ExecutionResponse(false, "Отмена...");
            }

            console.println("* Вставка элемента по индексу " + insertIndex + ":");
            Dragon dragon = Ask.askDragon(console, collectionManager.getFreeId());

            if (dragon != null && dragon.validate()) {
                console.println(collectionManager.insert(dragon, insertIndex));
                console.println("Дракон по индексу " + insertIndex + " успешно добавлен!");
                return new ExecutionResponse(true, "Дракон по индексу " + insertIndex + " успешно добавлен!");
            } else {
                console.printError("Поля дракона не валидны! Дракон не добавлен.");
                return new ExecutionResponse(false, "Поля дракона не валидны! Дракон не добавлен.");
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
        }
    }
}
