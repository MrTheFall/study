package org.lab6.commands;

import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.Map;

/**
 * Команда 'sort'. Сортирует коллекцию в естественном порядке.
 */
public class Sort extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public Sort(Console console, CollectionManager collectionManager) {
        super("sort", "сортировка коллекции в естественном порядке");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду сортировки.
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        try {
            console.println("* Сортировка коллекции:");
            collectionManager.sort();
            console.println("Коллекция успешно отсортирована!");
            return new Response(true, "Коллекция успешно отсортирована.");
        } catch (Exception e) {
            console.printError("Возникла ошибка при попытке сортировки коллекции: " + e.getMessage());
            return new Response(false, "Возникла ошибка при попытке сортировки коллекции!");
        }
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>();
    }
}
