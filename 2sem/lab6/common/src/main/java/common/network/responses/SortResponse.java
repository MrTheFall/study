package common.network.responses;

import common.utils.Commands;

public class SortResponse extends Response {

    public final boolean success;

    public SortResponse(boolean success, String error) {
        super(Commands.SHOW, error);
        this.success = success;
    }
}
