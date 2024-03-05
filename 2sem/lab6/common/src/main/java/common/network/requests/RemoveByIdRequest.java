package common.network.requests;

import common.models.Dragon;
import common.utils.Commands;

public class RemoveByIdRequest extends Request {
    public final int id;

    public RemoveByIdRequest(int id) {
        super(Commands.REMOVE_BY_ID);
        this.id = id;
    }
}
