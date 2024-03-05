package common.network.requests;

import common.utils.Commands;

public class SortRequest extends Request {
    public SortRequest() {
        super(Commands.SORT);
    }
}
