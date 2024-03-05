package common.network.responses;

import common.utils.Commands;

import java.util.Vector;

public class UpdateResponse extends Response{
    public final boolean success;

    public UpdateResponse(boolean success, String error) {
        super(Commands.UPDATE, error);
        this.success = success;
    }
}
