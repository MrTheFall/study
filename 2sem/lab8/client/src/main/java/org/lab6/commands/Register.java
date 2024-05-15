package org.lab6.commands;

import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

    public class Register extends Command implements Serializable {
        private static final long serialVersionUID = 302740660234595209L;
        public Register() {
        super("register", "");
    }

    @Override
    public ArrayList<ArgumentType> getArgumentType() {
        return null;
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
