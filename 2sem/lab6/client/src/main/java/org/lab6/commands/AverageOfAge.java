package org.lab6.commands;

import common.exceptions.APIException;
import common.network.requests.AverageOfAgeRequest;
import common.network.responses.AverageOfAgeResponse;
import org.lab6.network.TCPClient;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

/**
 * Команда 'average_of_age'. Вычисляет средний возраст всех драконов в коллекции.
 */
public class AverageOfAge extends Command {
    private final Console console;
    private final TCPClient client;

    /**
     * @param console
     * @param collectionManager
     */
    public AverageOfAge(Console console, TCPClient client) {
        super("average_of_age", "вычислить средний возраст всех драконов в коллекции");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду.
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return new ExecutionResponse(false, "Неверное использование команды show");
        }
        try {
            var response = (AverageOfAgeResponse) client.sendAndReceiveCommand(new AverageOfAgeRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }
            if (response.averageAge == 0) {
                console.println("Коллекция пуста, не удалось вычислить средний возраст!");
                return new ExecutionResponse(true, "Коллекция пуста, не удалось вычислить средний возраст");
            }
            console.println("Средний возраст драконов: " + response.averageAge);
            return new ExecutionResponse(true, "Средний возраст драконов: " + response.averageAge);
        } catch (Exception e) {
            console.printError("Произошла ошибка во время вычисления среднего возраста");
            return new ExecutionResponse(false, "Произошла ошибка во время вычисления среднего возраста");
        }
    }
}