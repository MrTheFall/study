package org.lab6.commands;

import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.Map;

/**
 * Команда 'save'. Сохраняет коллекцию.
 */
public class Save extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     */
    public Save(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        console.println("Сохранение недоступно...");
        //collectionManager.saveCollection();
        return new Response(true, "Сохранение недоступно...");
    }
    public ArrayList<ArgumentType> getArgumentType(){
        return new ArrayList<>();
    };

    public AccessType getAccessType(){
        return AccessType.READ;
    }
}
