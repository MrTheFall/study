package org.lab6.commands;

import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Info extends Command implements Serializable {
    private static final long serialVersionUID = 555644535434151212L;
    public Info() {
    super("info", "");
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