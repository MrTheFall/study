package org.lab6.commands;

import common.exceptions.APIException;
import common.models.Dragon;
import common.network.requests.AddIfMaxRequest;
import common.network.requests.AddRequest;
import common.network.responses.AddIfMaxResponse;
import common.network.responses.AddResponse;
import org.lab6.utils.Ask;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;
import org.lab6.network.TCPClient;

import java.io.IOException;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 */
public class AddIfMax extends Command {
    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param client
     */
    public AddIfMax(Console console, TCPClient client) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
            console.println("* Создание нового продукта (add_if_max):");
            Dragon dragon = Ask.askDragon(console);
            if (dragon != null && dragon.validate()) {
                var response = (AddIfMaxResponse) client.sendAndReceiveCommand(new AddIfMaxRequest(dragon));
                if (!response.isAdded){
                    console.println("Дракон не был добавлен: " + response.getError());
                    return new ExecutionResponse(false, "Дракон не был добавлен: " + response.getError());
                }
                if (response.getError() != null && !response.getError().isEmpty()) {
                    throw new APIException(response.getError());
                }
                console.println("Дракон успешно добавлен!");
                return new ExecutionResponse("Дракон успешно добавлен!");
            }
            return new ExecutionResponse(true, "Дракон успешно добавлен!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
        } catch (APIException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
