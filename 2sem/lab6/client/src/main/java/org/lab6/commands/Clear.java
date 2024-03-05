package org.lab6.commands;

import common.exceptions.APIException;
import common.network.requests.ClearRequest;
import common.network.responses.ClearResponse;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.io.IOException;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final Console console;
    private final TCPClient client;

    public Clear(Console console, TCPClient client) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return new ExecutionResponse(false, "Неправильное количество аргументов!");
        }
        try {
            var response = (ClearResponse) client.sendAndReceiveCommand(new ClearRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }
            if (!response.success) {
                console.println("Не удалось очистить коллекцию!");
                return new ExecutionResponse(false, "Не удалось очистить коллекцию");
            }
            console.println("Коллекция очищена!");
            return new ExecutionResponse(true, "Коллекция очищена!");
        } catch (APIException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
