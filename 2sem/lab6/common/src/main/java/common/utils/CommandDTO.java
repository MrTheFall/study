package common.utils;

import common.models.Dragon;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class CommandDTO extends Command {

    /**
     * @param name
     * @param description
     */
    public ArrayList<ArgumentType> argumentType;
    public CommandDTO(String name, String description, ArrayList<ArgumentType> argumentType) {
        super(name, description);
        this.argumentType = argumentType;
    }


    @Override
    public ArrayList<ArgumentType> getArgumentType() {
        return argumentType;
    }

    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        return null;
    }
}
