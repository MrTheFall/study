package org.lab6.commands;

import common.network.Request;
import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class GenericCommand extends Command {
    /**
     * @param name
     */
    public ArrayList<ArgumentType> argumentType;
    public GenericCommand(String name, String description, ArrayList<ArgumentType> argumentType) {
        super(name, description);
        this.argumentType = argumentType;
    }

    @Override
    public ArrayList<ArgumentType> getArgumentType(){
        return argumentType;
    }

    @Override
    public AccessType getAccessType() {
        return AccessType.NONE;
    }

    ;
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        return new Response(true, null);
    }
}
