package org.lab6.commands;

import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.Map;

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
    public Response apply(Map<ArgumentType, Object> args) {
        try {
            double averageAge = collectionManager.averageOfAge();

            if (Double.isNaN(averageAge)) { // Проверяем, что результат не NaN, т.е. в коллекции есть элементы
                console.printError("Коллекция пуста, не удалось вычислить средний возраст.");
                return new Response(true, "Коллекция пуста, не удалось вычислить средний возраст.");
            }

            console.println("Средний возраст драконов в коллекции: " + averageAge);
            return new Response(true, "Средний возраст драконов в коллекции: " + averageAge);

        } catch (Exception e) {
            console.printError("Произошла ошибка во время вычисления среднего возраста");
            return new Response(false, "Произошла ошибка во время вычисления среднего возраста");
        }
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>();
    }
}