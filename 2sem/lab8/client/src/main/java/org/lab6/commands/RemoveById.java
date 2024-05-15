package org.lab6.commands;

import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoveById extends Command implements Serializable {
    private static final long serialVersionUID = 149142081420L;
    public RemoveById() {
    super("remove_by_id", "");
}

@Override
public ArrayList<ArgumentType> getArgumentType() {
    return new ArrayList<>(List.of(ArgumentType.AUTH_SESSION, ArgumentType.ID));
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
