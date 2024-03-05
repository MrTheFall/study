package org.lab6.commands;

import common.exceptions.APIException;
import common.network.requests.ShowRequest;
import common.network.requests.SortRequest;
import common.network.responses.ShowResponse;
import common.network.responses.SortResponse;
import org.lab6.managers.CollectionManager;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

/**
 * Команда 'sort'. Сортирует коллекцию в естественном порядке.
 */
public class Sort extends Command {
    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param client
     */
    public Sort(Console console, TCPClient client) {
        super("sort", "сортировка коллекции в естественном порядке");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду сортировки.
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            var response = (SortResponse) client.sendAndReceiveCommand(new SortRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }
            console.println("* Сортировка коллекции:");
            if (!response.success) {
                console.printError("Произошла ошибка при попытке отсортировать коллекцию.");
                return new ExecutionResponse(false, "Произошла ошибка при попытке отсортировать коллекцию.");
            }
            console.println("Коллекция успешно отсортирована!");
            return new ExecutionResponse(true, "Коллекция успешно отсортирована!");
        } catch (Exception e) {
            console.printError("Возникла ошибка при попытке сортировки коллекции: " + e.getMessage());
            return new ExecutionResponse(false, "Возникла ошибка при попытке сортировки коллекции: " + e.getMessage());
        }
    }
}
