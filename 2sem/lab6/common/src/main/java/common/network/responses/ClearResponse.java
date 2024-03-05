package common.network.responses;

import common.utils.Commands;

public class ClearResponse extends Response {
    public final boolean success;

    public ClearResponse(boolean success, String error) {
        super(Commands.CLEAR, error);
        this.success = success;
    }
}