package org.lab6.commands;

import common.exceptions.APIException;
import common.network.requests.UpdateRequest;
import common.network.responses.UpdateResponse;
import org.lab6.network.TCPClient;
import org.lab6.utils.Ask;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.io.IOException;

/**
 * Команда 'update'. Обновляет значение элемента коллекции по ID.
 */
public class Update extends Command {
    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param client
     */
    public Update(Console console, TCPClient client) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
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
                return new ExecutionResponse(false, "Отмена...");
            }

            console.println("* Создание нового Дракона:");
            var d = Ask.askDragon(console);
            if (d != null && d.validate()) {
                var response = (UpdateResponse) client.sendAndReceiveCommand(new UpdateRequest(id, d));
                if (response.getError() != null && !response.getError().isEmpty()) {
                    console.println("Произошла ошибка при обновлении дракона: " + response.getError());
                    return new ExecutionResponse("Произошла ошибка при обновлении дракона: " + response.getError());
                }
                console.println("Дракон успешно обновлён!");
                return new ExecutionResponse("Дракон успешно обновлён!");
            } else
                return new ExecutionResponse(false, "Поля дракона не валидны! Дракон не создан!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
