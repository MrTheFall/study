package org.lab6.managers;

import common.models.Dragon;
import common.network.Response;
import org.lab6.utils.console.Console;

public class ResponseHandler {

    public void handle(Console console, Response response) {
        if (response.isSuccess()) {
            if (response.getMessage() != null) console.println(response.getMessage());

            // Check if dragons vector is not null and not empty, then print its content
            if (response.getDragons() != null && !response.getDragons().isEmpty()) {
                for (Dragon dragon : response.getDragons()) {
                    console.println(dragon);  // Assumes Dragon class has a sensible toString() implementation
                }
            }
        } else {
            // Since the response is unsuccessful, we print the error message
            console.printError(response.getMessage());
        }
    }
}

