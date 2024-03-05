package common.network.requests;

import common.utils.Commands;

public class PrintUniqueColorRequest extends Request {
    public PrintUniqueColorRequest() {
        super(Commands.PRINT_UNIQUE_COLOR);
    }
}
