package org.lab5.commands;

import org.lab5.utils.ExecutionResponse;
import org.lab5.utils.console.Console;

/**
 * Команда 'execute_script'. Выполнить скрипт из файла.
 * @author maxbarsukov
 */
public class ExecuteScript extends Command {
    private final Console console;

    public ExecuteScript(Console console) {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return new ExecutionResponse(true, "Некорректное использование...");
        }

        console.println("Выполнение скрипта '" + arguments[1] + "'...");
        return new ExecutionResponse(true, "Выполнение скрипта...");

    }
}

