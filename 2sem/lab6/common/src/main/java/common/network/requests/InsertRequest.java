package common.network.requests;

import common.models.Dragon;
import common.utils.Commands;

public class InsertRequest extends Request{
    public final int insertIndex;
    public final Dragon dragon;

    public InsertRequest(int insertIndex, Dragon dragon) {
        super(Commands.INSERT);
        this.insertIndex = insertIndex;
        this.dragon = dragon;
    }
}
