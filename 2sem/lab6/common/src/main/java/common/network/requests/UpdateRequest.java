package common.network.requests;

import common.models.Dragon;
import common.utils.Commands;

public class UpdateRequest extends Request {
    public final int id;
    public final Dragon dragon;

    public UpdateRequest(int id, Dragon dragon) {
        super(Commands.UPDATE);
        this.id = id;
        this.dragon = dragon;
    }
}