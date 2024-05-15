package org.lab6.commands;

import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Clear extends Command implements Serializable {
    private static final long serialVersionUID = 611412959523352L;
    public Clear() {
    super("clear", "");
}

@Override
public ArrayList<ArgumentType> getArgumentType() {
    return new ArrayList<>(List.of(ArgumentType.AUTH_SESSION));
}

@Override
public AccessType getAccessType() {
    return null;
}

@Override
public Response apply(Map<ArgumentType, Object> args) {
    return null;
}
}
