package common.network;

import common.models.Dragon;
import common.utils.ArgumentType;
import common.utils.Command;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class Request implements Serializable {
    private Command command;
    private Map<ArgumentType, Object> args;

    public Request(Command command, Map<ArgumentType, Object> args) {
        this.command = command;
        this.args = args;
    }

    public Command getCommand() {
        return command;
    }

    public Map<ArgumentType, Object> getArguments() {
        return args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request response = (Request) o;
        return Objects.equals(command, response.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                '}';
    }
}