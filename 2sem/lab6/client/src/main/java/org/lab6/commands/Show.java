package org.lab6.commands;

import common.exceptions.APIException;
import common.network.Request;
import common.network.Response;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.io.IOException;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 */
public class Show extends Command {
    private final Console console;
    private final TCPClient client;

    public Show(Console console, TCPClient client) {
        super("show", "вывести все элементы коллекции");
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
            return new ExecutionResponse(false, "Неверное использование команды show");
        }
        try {
            var response = (Response) client.sendAndReceiveCommand(new Request(this.getName(), arguments, null));
            if (!response.isSuccess()) {
                throw new APIException(response.getMessage());
            }
            if (response.getDragons().isEmpty()) {
                console.println("Коллекция пуста!");
                return new ExecutionResponse(true, "Коллекция пуста");
            }
            for (var dragon : response.getDragons()) {
                console.println(dragon + "\n");
            }
        } catch (APIException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ExecutionResponse(true, "Коллекция была выведена в виде строки   ");
    }
}
