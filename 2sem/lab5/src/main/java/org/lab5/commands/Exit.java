package org.lab5.commands;

import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;

/**
 * Команда 'exit'. Завершает программу без сохранения в файл.
 */
public class Exit extends Command {
    private final Console console;

    /**
     * @param console
     */
    public Exit(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.printError("Неправильное количество аргументов!");
            console.printError("Использование: '" + getName() + "'");
            return new ExecutionResponse(false, "Неправильное количество аргументов!");
        }

        console.println("Завершение выполнения...");
        return new ExecutionResponse(true, "Завершение выполнения...");
    }
}
