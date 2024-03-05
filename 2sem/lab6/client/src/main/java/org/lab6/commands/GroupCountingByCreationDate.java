package org.lab6.commands;

import common.exceptions.APIException;
import common.network.requests.GroupCountingByCreationDateRequest;
import common.network.responses.GroupCountingByCreationDateResponse;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.io.IOException;

/**
 * Команда 'group_counting_by_creation_date'. Группирует элементы коллекции по дате создания.
 */
public class GroupCountingByCreationDate extends Command {
    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param client
     */
    public GroupCountingByCreationDate(Console console, TCPClient client) {
        super("group_counting_by_creation_date", "группировать элементы коллекции по дате создания");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            var response = (GroupCountingByCreationDateResponse) client.sendAndReceiveCommand(new GroupCountingByCreationDateRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }
            if (response.groups.isEmpty()) {
                console.println("Коллекция пуста.");
                return new ExecutionResponse(true, "Коллекция пуста.");
            }
            response.groups.forEach((date, dragons) -> {
                console.println("Дата создания: " + date + " | Количество драконов: " + dragons.size());
            });

            return new ExecutionResponse(true, "Элементы коллекции успешно сгруппированы по дате создания.");
        } catch (APIException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

