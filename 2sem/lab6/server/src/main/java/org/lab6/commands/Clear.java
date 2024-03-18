package org.lab6.commands;

import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import org.apache.logging.log4j.Logger;
import org.lab6.Main;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.Map;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Logger logger = Main.logger; // Ensure this logger is initialized properly

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
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
            collectionManager.clear();
            return new Response(true, "Коллекция очищена.");
        } catch (Exception e) {
            console.println("Ошибка при очистке коллекции!");
            return new Response(false, "Ошибка при очистке коллекции!");
        }
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>();
    }
}
