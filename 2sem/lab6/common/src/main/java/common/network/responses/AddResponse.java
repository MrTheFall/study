package common.network.responses;

import common.utils.Commands;

public class AddResponse extends Response {
    public final boolean success;

    public AddResponse(boolean success, String error) {
        super(Commands.ADD, error);
        this.success = success;
    }
}