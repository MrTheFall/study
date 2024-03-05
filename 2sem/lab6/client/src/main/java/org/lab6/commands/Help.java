package org.lab6.commands;

import common.exceptions.APIException;
import common.network.requests.HelpRequest;
import common.network.responses.HelpResponse;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.io.IOException;

/**
 * Команда 'help'. Выводит справку по доступным командам
 */
public class Help extends Command {
    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param client
     */
    public Help(Console console, TCPClient client) {
        super("help", "вывести справку по доступным командам");
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
            return new ExecutionResponse("Выведена справка по команде"+ getName());
        }
        try {
            var response = (HelpResponse) client.sendAndReceiveCommand(new HelpRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }
            if (response.commands.isEmpty()) {
                console.println("Не удалось вывести справку!");
                return new ExecutionResponse(false, "Не удалось вывести справку");
            }
            response.commands.entrySet().forEach(command -> {
                console.printTable(command.getKey(), command.getValue());
            });
            return new ExecutionResponse(true, "Выведена справка!");
        } catch (APIException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
