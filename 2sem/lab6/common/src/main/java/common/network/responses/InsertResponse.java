package common.network.responses;

import common.utils.Commands;

public class InsertResponse extends Response {
    public final boolean success;

    public InsertResponse(boolean success, String error) {
        super(Commands.UPDATE, error);
        this.success = success;
    }
}
