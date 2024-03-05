package org.lab6.commands;

import common.exceptions.APIException;
import common.network.requests.PrintUniqueColorRequest;
import common.network.responses.PrintUniqueColorResponse;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.io.IOException;

/**
 * Команда 'print_unique_color'. Выводит уникальные значения поля color всех элементов в коллекции.
 */
public class PrintUniqueColor extends Command {
    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param client
     */
    public PrintUniqueColor(Console console, TCPClient client) {
        super("print_unique_color", "вывести уникальные значения поля color всех элементов в коллекции");
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
            var response = (PrintUniqueColorResponse) client.sendAndReceiveCommand(new PrintUniqueColorRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }
            if (response.colors.isEmpty()) {
                console.println("В коллекции нет элементов, поэтому уникальные цвета отсутствуют.");
                return new ExecutionResponse(true, "Уникальные цвета отсутствуют.");
            }
            console.println("Уникальные цвета драконов в коллекции:");
            response.colors.forEach(color -> {
                console.println("Цвет: " + color);
            });
        } catch (APIException | IOException e) {
            throw new RuntimeException(e);
        }
        return new ExecutionResponse(true, "Коллекция была выведена в виде строки   ");
    }
}
