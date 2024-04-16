package org.lab6.commands;

import common.models.Dragon;
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
 * Команда 'insert'. Вставляет новый элемент в заданное место коллекции.
 */
public class Insert extends Command {

    private final Console console;
    private final CollectionManager collectionManager;
    private final DatabaseManager databaseManager;

    /**
     * @param console
     * @param collectionManager
     */
    public Insert(Console console, CollectionManager collectionManager, DatabaseManager databaseManager) {
        super("insert <ID> {element}", "вставить новый элемент в заданное место коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.databaseManager = databaseManager;
    }

    /**
     * Выполняет команду.
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        try {
            int insertIndex = (int) args.get(ArgumentType.INDEX);
            if (insertIndex < 0 ){
                insertIndex = 0;
            }
            if (insertIndex > collectionManager.getCollection().size()){
                insertIndex = collectionManager.getCollection().size();
            }
            Dragon newDragon = (Dragon) args.get(ArgumentType.DRAGON);
            if (newDragon == null) {
                console.println("Дракон не передан!");
                return new Response(false, "Дракон не передан!");
            }
            int id = databaseManager.addDragon(newDragon);
            if (id == -1) return new Response(false, "Ошибка при добавлении нового дракона.");
            //newDragon.setId(collectionManager.getFreeId());
            if (newDragon.validate()) {
                collectionManager.insert(newDragon, insertIndex);
                console.println("Дракон по индексу " + insertIndex + " успешно добавлен!");
                return new Response(true, "Дракон по индексу " + insertIndex + " успешно добавлен!");
            } else {
                console.printError("Поля дракона не валидны! Дракон не добавлен.");
                return new Response(false, "Поля дракона не валидны! Дракон не добавлен.");
            }
        } catch (Exception e) {
            console.printError(e.toString());
            return new Response(false, "Произошла ошибка при вставке дракона!");
        }
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.AUTH_SESSION, ArgumentType.INDEX, ArgumentType.DRAGON));
    }
    public AccessType getAccessType(){
        return AccessType.WRITE;
    }
}
