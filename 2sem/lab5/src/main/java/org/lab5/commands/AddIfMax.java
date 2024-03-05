package org.lab5.commands;

import org.lab5.managers.CollectionManager;
import org.lab5.models.Dragon;
import org.lab5.utils.Ask;
import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;

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
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) {
                console.printError("Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
                return new ExecutionResponse(false,
                        "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            }
            console.println("* Создание нового продукта (add_if_max):");
            Dragon dragon = Ask.askDragon(console, collectionManager.getFreeId());
            int maxAge = maxAge();
            if (maxAge == -1 || dragon.getAge() > maxAge) {
                collectionManager.add(dragon);
                console.println("Дракон успешно добавлен!");
            } else {
                console.println("Дракон не добавлен, его возрасть не превышает возраст самого старого дракона (" + dragon.getAge() + " < " + maxAge + ")");
            }
            return new ExecutionResponse(true, "Дракон успешно добавлен!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
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
