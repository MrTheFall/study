package org.lab5.commands;

import org.lab5.managers.CollectionManager;
import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;

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
    public ExecutionResponse apply(String[] arguments) {
        try {
            // Вызываем метод averageOfAge у collectionManager и получаем средний возраст
            double averageAge = collectionManager.averageOfAge();

            if (Double.isNaN(averageAge)) { // Проверяем, что результат не NaN, т.е. в коллекции есть элементы
                console.printError("Коллекция пуста, не удалось вычислить средний возраст.");
                return new ExecutionResponse(false, "Коллекция пуста, не удалось вычислить средний возраст.");
            }

            // Выводим результат
            console.println("Средний возраст драконов в коллекции: " + averageAge);
            return new ExecutionResponse(true, "Средний возраст драконов: " + averageAge);

        } catch (Exception e) {
            console.printError("Произошла ошибка во время вычисления среднего возраста");
            return new ExecutionResponse(false, "Произошла ошибка во время вычисления среднего возраста");
        }
    }
}