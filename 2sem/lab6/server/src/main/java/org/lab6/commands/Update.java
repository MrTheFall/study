package org.lab6.commands;

import common.models.Dragon;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Response apply(Map<ArgumentType, Object> args) {
        try {
            int id = (int) args.get(ArgumentType.ID);
            var old = collectionManager.getById(id);
            if (old == null || !collectionManager.getCollection().contains(old)) {
                console.println("не существующий ID");
                return new Response(false, "не существующий ID!");
            }
            Dragon d = (Dragon) args.get(ArgumentType.DRAGON);
            d.setId(id);
            if (d != null && d.validate()) {
                collectionManager.remove(old.getId());
                collectionManager.add(d);
                collectionManager.update();
                return new Response(true, null);
            } else {
                console.println("Поля Дракона не валидны! Дракон не создан!");
                return new Response(false, "Поля Дракона не валидны! Дракон не обновлен!");
            }
        } catch (Exception e) {
            console.printError(e.toString());
            return new Response(false, e.toString());
        }
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.ID, ArgumentType.DRAGON));
    }
}
