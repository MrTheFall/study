package common.network.requests;

import common.utils.Commands;

public class InfoRequest extends Request {
    public InfoRequest() {
        super(Commands.INFO);
    }
}