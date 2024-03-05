package common.network.responses;

import common.utils.Commands;

public class AddIfMaxResponse extends Response {
    public final boolean isAdded;

    public AddIfMaxResponse(boolean isAdded, String error) {
        super(Commands.ADD_IF_MAX, error);
        this.isAdded = isAdded;
    }
}