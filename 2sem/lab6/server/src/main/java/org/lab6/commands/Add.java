package org.lab6.commands;

import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import common.models.Dragon;
import org.lab6.utils.console.Console;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console            интерфейс консоли
     * @param collectionManager  менеджер коллекции
     */
    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Ответ после выполнения команды.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        try {
            Dragon dragon = (Dragon) args.get(ArgumentType.DRAGON);
            if (dragon == null || !dragon.validate()) {
                return new Response(false, "Поля дракона не валидны! Дракон не создан!");
            }
            dragon.setId(collectionManager.getFreeId());
            boolean success = collectionManager.add(dragon);
            console.println("Дракон успешно добавлен!");
            return new Response(success, "Дракон успешно добавлен!");
        } catch (Exception e) {
            console.printError(e.toString());
            return new Response(false, e.toString());
        }
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.DRAGON));
    }

}






