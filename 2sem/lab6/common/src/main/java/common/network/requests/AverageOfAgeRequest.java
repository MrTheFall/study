package common.network.requests;

import common.models.Dragon;
import common.utils.Commands;

public class AverageOfAgeRequest extends Request{
    public AverageOfAgeRequest() {
        super(Commands.AVERAGE_OF_AGE);
    }

}
