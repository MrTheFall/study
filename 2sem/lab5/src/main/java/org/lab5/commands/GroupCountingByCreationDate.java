package org.lab5.commands;

import org.lab5.managers.CollectionManager;
import org.lab5.models.Dragon;
import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;

import java.time.LocalDateTime;
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
    public ExecutionResponse apply(String[] arguments) {
        Map<LocalDateTime, List<Dragon>> groups = collectionManager.groupCountingByCreationDate();
        if (groups.isEmpty()) {
            console.println("Коллекция пуста.");
            return new ExecutionResponse(true, "Коллекция пуста.");
        }

        groups.forEach((date, dragons) -> {
            console.println("Дата создания: " + date + " | Количество драконов: " + dragons.size());
        });

        return new ExecutionResponse(true, "Элементы коллекции успешно сгруппированы по дате создания.");
    }
}

