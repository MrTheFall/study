package org.lab6.commands;

import common.network.requests.AverageOfAgeRequest;
import common.network.requests.Request;
import common.network.responses.AverageOfAgeResponse;
import common.network.responses.Response;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

/**
 * Команда 'average_of_age'. Вычисляет средний возраст всех драконов в коллекции.
 */
public class AverageOfAge extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * @param console
     * @param collectionManager
     */
    public AverageOfAge(Console console, CollectionManager collectionManager) {
        super("average_of_age", "вычислить средний возраст всех драконов в коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response apply(Request request) {
        var req = (AverageOfAgeRequest) request;
        try {
            // Вызываем метод averageOfAge у collectionManager и получаем средний возраст
            double averageAge = collectionManager.averageOfAge();

            if (Double.isNaN(averageAge)) { // Проверяем, что результат не NaN, т.е. в коллекции есть элементы
                console.printError("Коллекция пуста, не удалось вычислить средний возраст.");
                return new AverageOfAgeResponse(0, "Коллекция пуста, не удалось вычислить средний возраст.");
            }

            // Выводим результат
            console.println("Средний возраст драконов в коллекции: " + averageAge);
            return new AverageOfAgeResponse(averageAge, null);

        } catch (Exception e) {
            console.printError("Произошла ошибка во время вычисления среднего возраста");
            return new AverageOfAgeResponse(-1, "Произошла ошибка во время вычисления среднего возраста");
        }
    }
}