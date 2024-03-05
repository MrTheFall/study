package org.lab6.commands;

import common.exceptions.APIException;
import common.network.requests.AddRequest;
import common.network.responses.AddResponse;
import common.models.Dragon;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;
import org.lab6.utils.Ask;

import java.io.IOException;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param client
     */
    public Add(Console console, TCPClient client) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) {
                console.printError("Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
                return new ExecutionResponse(false,
                        "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            }
            console.println("* Создание нового Дракона:");
            Dragon dragon = Ask.askDragon(console);

            if (dragon != null && dragon.validate()) {
                var response = (AddResponse) client.sendAndReceiveCommand(new AddRequest(dragon));
                if (response.getError() != null && !response.getError().isEmpty()) {
                    throw new APIException(response.getError());
                }
                console.println("Дракон успешно добавлен!");
                return new ExecutionResponse("Дракон успешно добавлен!");
            } else
                return new ExecutionResponse(false, "Поля дракона не валидны! Дракон не создан!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
        } catch (APIException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
