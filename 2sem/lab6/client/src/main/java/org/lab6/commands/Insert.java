package org.lab6.commands;

import common.network.requests.InsertRequest;
import common.network.requests.UpdateRequest;
import common.network.responses.InsertResponse;
import common.network.responses.UpdateResponse;
import org.lab6.managers.CollectionManager;
import common.models.Dragon;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;
import org.lab6.utils.Ask;

import java.io.IOException;

/**
 * Команда 'insert'. Вставляет новый элемент в заданное место коллекции.
 */
public class Insert extends Command {

    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param client
     */
    public Insert(Console console, TCPClient client) {
        super("insert <ID> {element}", "вставить новый элемент в заданное место коллекции");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду.
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (arguments.length < 2 || arguments[1].isEmpty()) {
                console.printError("Укажите ID для вставки элемента!\nИспользование: '" + getName() + "'");
                return new ExecutionResponse(false,
                        "Укажите ID для вставки элемента!\nИспользование: '" + getName() + "'");
            }

            int insertIndex = -1;
            try {
                insertIndex = Integer.parseInt(arguments[1].trim());
            } catch (NumberFormatException e) {
                console.printError("ID не распознан");
                return new ExecutionResponse(false, "Отмена...");
            }

            console.println("* Вставка элемента по индексу " + insertIndex + ":");
            var d = Ask.askDragon(console);
            if (d != null && d.validate()) {
                var response = (InsertResponse) client.sendAndReceiveCommand(new InsertRequest(insertIndex, d));
                if (response.getError() != null && !response.getError().isEmpty()) {
                    console.println("Произошла ошибка при вставке дракона: " + response.getError());
                    return new ExecutionResponse("Произошла ошибка при вставке дракона: " + response.getError());
                }
                console.println("Дракон успешно вставлен!");
                return new ExecutionResponse("Дракон успешно вставлен!");
            } else
                return new ExecutionResponse(false, "Поля дракона не валидны! Дракон не создан!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
