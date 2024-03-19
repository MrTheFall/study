package org.lab6.commands;

import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по ID.
 */
public class RemoveById extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        int id = (int) args.get(ArgumentType.ID);
        if (collectionManager.getById(id) == null || !collectionManager.getCollection().contains(collectionManager.getById(id))) {
            console.println("Передан несуществующий ID!");
            return new Response(false, "Передан несуществующий ID!");
        }
        collectionManager.remove(id);
        collectionManager.update();
        console.println("Дракон успешно удалён.");
        return new Response(true, "Дракон успешно удалён.");
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.ID));
    }
}
