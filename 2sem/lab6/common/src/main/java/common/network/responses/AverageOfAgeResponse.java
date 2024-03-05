package common.network.responses;

import common.utils.Commands;

public class AverageOfAgeResponse extends Response {
    public final double averageAge;

    public AverageOfAgeResponse(double averageAge, String error) {
        super(Commands.AVERAGE_OF_AGE, error);
        this.averageAge = averageAge;
    }
}
