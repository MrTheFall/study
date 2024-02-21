package org.lab5.commands;

import org.lab5.managers.CollectionManager;
import org.lab5.models.Color;
import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;

import java.util.Set;

/**
 * Команда 'print_unique_color'. Выводит уникальные значения поля color всех элементов в коллекции.
 */
public class PrintUniqueColor extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public PrintUniqueColor(Console console, CollectionManager collectionManager) {
        super("print_unique_color", "вывести уникальные значения поля color всех элементов в коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        Set<Color> uniqueColors = collectionManager.getUniqueColors();
        if (uniqueColors.isEmpty()) {
            console.printError("В коллекции нет элементов, поэтому уникальные цвета отсутствуют.");
            return new ExecutionResponse(true, "Уникальные цвета отсутствуют.");
        }

        console.println("Уникальные цвета драконов в коллекции:");
        uniqueColors.forEach(color -> {
            console.println("Цвет: " + color);
        });

        return new ExecutionResponse(true, "Уникальные цвета успешно выведены.");
    }
}
