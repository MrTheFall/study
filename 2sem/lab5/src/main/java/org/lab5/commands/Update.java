package org.lab5.commands;

import org.lab5.managers.CollectionManager;
import org.lab5.utils.Ask;
import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;

/**
 * Команда 'update'. Обновляет значение элемента коллекции по ID.
 */
public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public Update(Console console, CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) {
                console.println("Неправильное количество аргументов!");
                console.println("Использование: '" + getName() + "'");
                return new ExecutionResponse(false, "Неправильное количество аргументов!");
            }
            int id = -1;
            try {
                id = Integer.parseInt(arguments[1].trim());
            } catch (NumberFormatException e) {
                console.println("ID не распознан");
                return new ExecutionResponse(false, "Отмена...");
            }

            var old = collectionManager.getById(id);
            if (old == null || !collectionManager.getCollection().contains(old)) {
                console.println("не существующий ID");
                return new ExecutionResponse(false, "не существующий ID!");
            }

            console.println("* Создание нового Дракона:");
            var d = Ask.askDragon(console, old.getId());
            if (d != null && d.validate()) {
                collectionManager.remove(old.getId());
                collectionManager.add(d);
                collectionManager.update();
                return new ExecutionResponse(true, "Обновление дракона!");

            } else {
                console.println("Поля Дракона не валидны! Дракон не создан!");
                return new ExecutionResponse(false, "Поля Дракона не валидны! Дракон не обновлен!");

            }
        } catch (Ask.AskBreak e) {
            console.println("Отмена...");
            return new ExecutionResponse(false, "Отмена...");
        }
    }
}
