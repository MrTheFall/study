package common.network.requests;

import common.utils.Commands;

public class ClearRequest extends Request {
    public ClearRequest() {
        super(Commands.CLEAR);
    }
}