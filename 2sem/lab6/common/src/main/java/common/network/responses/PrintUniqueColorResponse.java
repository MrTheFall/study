package common.network.responses;

import common.models.Color;
import common.utils.Commands;

import java.util.Set;

public class PrintUniqueColorResponse extends Response {
    public final Set<Color> colors;
    public PrintUniqueColorResponse(Set<Color> colors, String error) {
        super(Commands.HELP, error);
        this.colors = colors;
    }

}
