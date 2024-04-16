package org.lab6.commands;

import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;
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
        System.exit(0);
        return new Response(true, "Завершение выполнения...");
    }
    public ArrayList<ArgumentType> getArgumentType(){
        return new ArrayList<>();
    };

    public AccessType getAccessType(){
        return AccessType.NONE;
    }
}
