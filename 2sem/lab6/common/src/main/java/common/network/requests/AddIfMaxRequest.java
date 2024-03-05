package common.network.requests;

import common.models.Dragon;
import common.utils.Commands;

public class AddIfMaxRequest extends Request {
    public final Dragon dragon;

    public AddIfMaxRequest(Dragon dragon) {
        super(Commands.ADD_IF_MAX);
        this.dragon = dragon;
    }
}