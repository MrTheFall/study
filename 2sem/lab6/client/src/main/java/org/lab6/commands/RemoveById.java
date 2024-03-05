package org.lab6.commands;

import common.network.requests.RemoveByIdRequest;
import common.network.responses.RemoveByIdResponse;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.io.IOException;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по ID.
 */
public class RemoveById extends Command {
    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param collectionManager
     */
    public RemoveById(Console console, TCPClient client) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return new ExecutionResponse(false, "Неправильное количество аргументов!");
        }
        int id = -1;
        try {
            id = Integer.parseInt(arguments[1].trim());
        } catch (NumberFormatException e) {
            console.println("ID не распознан");
            return new ExecutionResponse(false, "ID не распознан");
        }
        RemoveByIdResponse response = null;
        try {
            response = (RemoveByIdResponse) client.sendAndReceiveCommand(new RemoveByIdRequest(id));
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.println("Произошла ошибка при удалении дракона: " + response.getError());
                return new ExecutionResponse("Произошла ошибка при удалении дракона: " + response.getError());
            }
            console.println("Дракон с id=" + id + " успешно удалён!");
            return new ExecutionResponse("Дракон с id=" + id + " успешно удалён!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
