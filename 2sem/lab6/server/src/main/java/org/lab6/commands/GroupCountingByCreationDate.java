package org.lab6.commands;

import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import common.models.Dragon;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Команда 'group_counting_by_creation_date'. Группирует элементы коллекции по дате создания.
 */
public class GroupCountingByCreationDate extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public GroupCountingByCreationDate(Console console, CollectionManager collectionManager) {
        super("group_counting_by_creation_date", "группировать элементы коллекции по дате создания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        Map<LocalDateTime, List<Dragon>> groups = collectionManager.groupCountingByCreationDate();
        if (groups.isEmpty()) {
            console.println("Коллекция пуста.");
            return new Response(true, "Коллекция пуста", groups);
        }

        groups.forEach((date, dragons) -> {
            console.println("Дата создания: " + date + " | Количество драконов: " + dragons.size());
        });

        return new Response(true, "Группировка коллекции по дате создания:", groups);
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>();
    }
}