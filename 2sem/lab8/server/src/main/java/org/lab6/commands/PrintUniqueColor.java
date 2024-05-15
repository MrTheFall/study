package org.lab6.commands;

import common.models.Dragon;
import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import common.models.Color;
import org.lab6.utils.console.Console;

import java.util.*;

/**
 * Команда 'print_unique_color'. Выводит уникальные значения поля color всех элементов в коллекции.
 */
public class PrintUniqueColor extends Command {
    private static final long serialVersionUID = 84814142412L;
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
    public Response apply(Map<ArgumentType, Object> args) {
        Set<Color> uniqueColors = collectionManager.getUniqueColors();
        if (uniqueColors.isEmpty()) {
            console.println("В коллекции нет элементов, поэтому уникальные цвета отсутствуют.");
            return new Response(true, "В коллекции нет элементов, поэтому уникальные цвета отсутствуют.", uniqueColors);
        }
        console.println("Уникальные цвета драконов в коллекции:");
        uniqueColors.forEach(console::println);

        StringBuilder sb = new StringBuilder("Уникальные цвета драконов в коллекции:");
        for (Color color : uniqueColors) {
            sb.append(", ").append(color.toString());
        }

        String uniqueColorsString = sb.toString();

        return new Response(true, uniqueColorsString, uniqueColors);
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.AUTH_SESSION));
    }

    public AccessType getAccessType(){
        return AccessType.WRITE;
    }
}
