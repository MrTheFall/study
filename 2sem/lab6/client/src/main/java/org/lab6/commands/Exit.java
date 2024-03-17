package org.lab6.commands;

import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.utils.ExecutionResponse;
import org.lab6.utils.console.Console;

import java.util.ArrayList;
import java.util.Map;

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
    public Response apply(Map<ArgumentType, Object> args) {
        console.println("Завершение выполнения...");
        return new Response(true, "Завершение выполнения...");
    }
    public ArrayList<ArgumentType> getArgumentType(){
        return new ArrayList<>();
    };

}
