package common.network.requests;


import common.models.Dragon;
import common.utils.Commands;

public class AddRequest extends Request {
    public final Dragon dragon;

    public AddRequest(Dragon dragon) {
        super(Commands.ADD);
        this.dragon = dragon;
    }
}