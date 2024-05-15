package org.lab6.commands;

import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddIfMax extends Command implements Serializable {
    private static final long serialVersionUID = 5832098121321231L;
    public AddIfMax() {
    super("add_if_max", "");
}

@Override
public ArrayList<ArgumentType> getArgumentType() {
    return new ArrayList<>(List.of(ArgumentType.AUTH_SESSION, ArgumentType.DRAGON));
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
