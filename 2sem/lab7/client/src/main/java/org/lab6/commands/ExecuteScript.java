package org.lab6.commands;

import common.network.Request;
import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.Map;

/**
 * Команда 'execute_script'. Выполнить скрипт из файла.
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
    public Response apply(Map<ArgumentType, Object> args) {
        if (args.get(ArgumentType.SCRIPT_NAME) != null) {
            console.println("Использование: '" + getName() + "'");
            return new Response(true, "Некорректное использование...");
        }

        console.println("Выполнение скрипта '" + args.get(ArgumentType.SCRIPT_NAME) + "'...");
        return new Response(true, "Выполнение скрипта...");

    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>();
    }

    @Override
    public AccessType getAccessType() {
        return AccessType.NONE;
    }

    ;

}

