package org.lab6.commands;

import java.io.IOException;

import common.exceptions.APIException;
import common.network.requests.InfoRequest;
import common.network.responses.InfoResponse;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;
/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final Console console;
    private final TCPClient client;

    public Info(Console console, TCPClient client) {
        super("info", "вывести информацию о коллекции");
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
            console.println("Использование: '" + getName() + "'");
            return new ExecutionResponse(false, "Неверное использование команды info");
        }
        try {
            var response = (InfoResponse) client.sendAndReceiveCommand(new InfoRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }
            console.println("Сведения о коллекции:");
            console.println(" Тип: " + response.collectionType);
            console.println(" Количество элементов: " + response.collectionSize);
            console.println(" Дата последнего сохранения: " + response.lastSaveTimeString);
            console.println(" Дата последней инициализации: " + response.lastInitTimeString);
        } catch (APIException | IOException e) {
            throw new RuntimeException(e);
        }
        return new ExecutionResponse(true, "Коллекция была выведена в виде строки   ");
    }
}