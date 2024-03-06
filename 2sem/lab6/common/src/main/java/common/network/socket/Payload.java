package common.network.socket;

import common.models.Dragon;

import java.io.Serializable;
import java.util.Vector;

public class Payload implements Serializable {
    String command = null;

    Vector<Dragon> dragons = null;
    String string = null;

    public Payload(String command, Vector<Dragon> dragons, String string) {
        this.command = command;
        this.dragons = dragons;
        this.string = string;
    }

    public String getCommand() {
        return command;
    }

    public Vector<Dragon> getDragons() {
        return dragons;
    }

    public String getString() {
        return string;
    }
}