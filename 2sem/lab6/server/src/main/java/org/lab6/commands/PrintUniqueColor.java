package org.lab6.commands;

import common.network.requests.PrintUniqueColorRequest;
import common.network.requests.Request;
import common.network.responses.PrintUniqueColorResponse;
import common.network.responses.Response;
import org.lab6.managers.CollectionManager;
import common.models.Color;
import org.lab6.utils.console.Console;

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
    public Response apply(Request request) {
        var req = (PrintUniqueColorRequest) request;
        Set<Color> uniqueColors = collectionManager.getUniqueColors();
        if (uniqueColors.isEmpty()) {
            console.println("В коллекции нет элементов, поэтому уникальные цвета отсутствуют.");
            return new PrintUniqueColorResponse(uniqueColors, null);
        }

        console.println("Уникальные цвета драконов в коллекции:");
        uniqueColors.forEach(color -> {
            console.println("Цвет: " + color);
        });
        return new PrintUniqueColorResponse(uniqueColors, null);
    }
}
