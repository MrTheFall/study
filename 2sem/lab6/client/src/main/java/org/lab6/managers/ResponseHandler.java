package org.lab6.managers;

import common.models.Dragon;
import common.network.Response;
import common.utils.Command;
import org.lab6.utils.console.Console;

public class ResponseHandler {

    public void handle(Console console, Response response) {
        if (response.isSuccess()) {
            if (response.getMessage() != null) console.println(response.getMessage());

            if (response.getDragons() != null && !response.getDragons().isEmpty()) {
                for (Dragon dragon : response.getDragons()) {
                    console.println(dragon);
                }
            }
            if (response.getCommands() != null && !response.getCommands().isEmpty()) {
                for (Command command : response.getCommands()) {
                    console.printTable(command.getName(), command.getDescription());
                }
            }
        } else {
            console.printError(response.getMessage());
        }
    }
}

