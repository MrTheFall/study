package common.network.requests;

import common.utils.Commands;

public class ShowRequest extends Request {
    public ShowRequest() {
        super(Commands.SHOW);
    }
}