package org.lab6.commands;

import common.exceptions.APIException;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.network.TCPClient;
import org.lab6.utils.console.Console;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 */
public class Show_REMOVE_LATER extends Command implements Serializable {

    private transient final Console console;

    private transient final TCPClient client;

    public Show_REMOVE_LATER(Console console, TCPClient client) {
        super("show", "вывести все элементы коллекции");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        /*if (!request.getArguments()[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return new Response(false, "Неверное использование команды show");
        }*/ // ВЫНЕСТИ ЭТО В ОТДЕЛЬНЫЙ ХЕНДЛЕР
        try {
            var response = (Response) client.sendAndReceiveCommand(new Request(this, args));
            if (!response.isSuccess()) {
                throw new APIException(response.getMessage());
            }
            if (response.getDragons().isEmpty()) {
                console.println("Коллекция пуста!");
                return new Response(true, "Коллекция пуста");
            }
            for (var dragon : response.getDragons()) {
                console.println(dragon + "\n");
            }
        } catch (APIException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new Response(true, "Коллекция была выведена в виде строки   ");
    }

    public ArrayList<ArgumentType> getArgumentType(){
        return new ArrayList<>();
    };
}
