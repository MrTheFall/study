package common.network;

import common.models.Dragon;

import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;

public class Request implements Serializable {
    private String command;
    private String[] arguments;
    private Vector<Dragon> dragons;

    public Request(String command, String[] arguments, Vector<Dragon> dragons) {
        this.command = command;
        this.arguments = arguments;
        this.dragons = dragons;
    }

    public String getCommand() {
        return command;
    }

    public Vector<Dragon> getDragons() {
        return dragons;
    }

    public String[] getArguments() {
        return arguments;
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