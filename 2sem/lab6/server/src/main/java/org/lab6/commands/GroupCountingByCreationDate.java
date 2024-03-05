package org.lab6.commands;

import common.network.requests.GroupCountingByCreationDateRequest;
import common.network.requests.Request;
import common.network.responses.GroupCountingByCreationDateResponse;
import common.network.responses.Response;
import org.lab6.managers.CollectionManager;
import common.models.Dragon;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

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
    public Response apply(Request request) {
        var req = (GroupCountingByCreationDateRequest) request;
        Map<LocalDateTime, List<Dragon>> groups = collectionManager.groupCountingByCreationDate();
        if (groups.isEmpty()) {
            console.println("Коллекция пуста.");
            return new GroupCountingByCreationDateResponse(groups, null);
        }

        groups.forEach((date, dragons) -> {
            console.println("Дата создания: " + date + " | Количество драконов: " + dragons.size());
        });

        return new GroupCountingByCreationDateResponse(groups, null);
    }
}

