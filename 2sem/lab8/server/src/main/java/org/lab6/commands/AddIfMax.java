package org.lab6.commands;

import common.models.Dragon;
import common.models.User;
import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import org.lab6.managers.DatabaseManager;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 */
public class AddIfMax extends Command {
    private static final long serialVersionUID = 1025393587770666927L;

    private final Console console;
    private final CollectionManager collectionManager;
    private final DatabaseManager databaseManager;

    /**
     * @param console
     * @param collectionManager
     */
    public AddIfMax(Console console, CollectionManager collectionManager, DatabaseManager databaseManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.databaseManager = databaseManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        try {
            User user = (User) args.get(ArgumentType.AUTH_SESSION);
            Dragon dragon = (Dragon) args.get(ArgumentType.DRAGON);
            int maxAge = maxAge();
            if (maxAge == -1 || dragon.getAge() > maxAge) {
                int id = databaseManager.addDragon(dragon, user);
                if (id == -1) return new Response(false, "Ошибка при добавлении нового дракона.");
                dragon.setId(id);
                //dragon.setId(collectionManager.getFreeId());
                collectionManager.add(dragon);
                console.println("Дракон успешно добавлен.");
            } else {
                console.println("Дракон не добавлен, его возрасть не превышает возраст самого старого дракона (" + dragon.getAge() + " < " + maxAge + ")");
                return new Response(false, "Дракон не добавлен, его возрасть не превышает возраст самого старого дракона (" + dragon.getAge() + " < " + maxAge + ")");
            }
            return new Response(true, "Дракон успешно добавлен.");
        } catch (Exception e) {
            return new Response(false, "Произошла ошибка при добавлении дракона!");
        }
    }

    private int maxAge() {
        return collectionManager.getCollection().stream()
                .map(Dragon::getAge)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1);
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.AUTH_SESSION, ArgumentType.DRAGON));
    }
    public AccessType getAccessType(){
        return AccessType.WRITE;
    }
}
