package common.network.requests;

import common.utils.Commands;

public class HelpRequest extends Request {
    public HelpRequest() {
        super(Commands.HELP);
    }
}