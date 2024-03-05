package common.network.responses;

import common.models.Dragon;
import common.utils.Commands;

import java.util.Vector;

public class ShowResponse extends Response {
    public final Vector<Dragon> dragons;

    public ShowResponse(Vector<Dragon> dragons, String error) {
        super(Commands.SHOW, error);
        this.dragons = dragons;
    }
}