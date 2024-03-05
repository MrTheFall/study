package common.network.responses;

import common.utils.Commands;

public class RemoveByIdResponse extends Response{
    public final boolean success;

    public RemoveByIdResponse(boolean success, String error) {
        super(Commands.UPDATE, error);
        this.success = success;
    }
}
