package common.network;

import common.models.Color;
import common.models.Dragon;
import common.models.User;
import common.utils.ArgumentType;
import common.utils.Command;

import java.io.Console;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Response implements Serializable {
    private final boolean success;
    private final String message;
    private final Vector<Dragon> dragons;
    private final ArrayList<Command> commands;
    private final Set<Color> uniqueColors;
    private final Map<LocalDateTime, List<Dragon>> dragonGroups;
    private final User user;

    public Response(boolean success, String message, Vector<Dragon> dragons, ArrayList<Command> commands, Set<Color> uniqueColors, Map<LocalDateTime, List<Dragon>> dragonGroups) {
        this.success = success;
        this.message = message;
        this.dragons = dragons;
        this.commands = commands;
        this.uniqueColors = uniqueColors;
        this.dragonGroups = dragonGroups;
        this.user = null;
    }
    public Response(boolean success, String message, Vector<Dragon> dragons, ArrayList<Command> commands) {
        this.success = success;
        this.message = message;
        this.dragons = dragons;
        this.commands = commands;
        this.uniqueColors = null;
        this.dragonGroups = null;
        this.user = null;
    }
    public Response(boolean success, String message, Vector<Dragon> dragons) {
        this.success = success;
        this.message = message;
        this.dragons = dragons;
        this.commands = null;
        this.uniqueColors = null;
        this.dragonGroups = null;
        this.user = null;
    }
    public Response(boolean success, String message, ArrayList<Command> commands) {
        this.success = success;
        this.message = message;
        this.dragons = null;
        this.commands = commands;
        this.uniqueColors = null;
        this.dragonGroups = null;
        this.user = null;
    }
    public Response(boolean success, String message, Set<Color> uniqueColors) {
        this.success = success;
        this.message = message;
        this.dragons = null;
        this.commands = null;
        this.uniqueColors = uniqueColors;
        this.dragonGroups = null;
        this.user = null;
    }
    public Response(boolean success, String message, Map<LocalDateTime, List<Dragon>> dragonGroups) {
        this.success = success;
        this.message = message;
        this.dragons = null;
        this.commands = null;
        this.uniqueColors = null;
        this.dragonGroups = dragonGroups;
        this.user = null;
    }
    public Response(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.dragons = null;
        this.commands = null;
        this.uniqueColors = null;
        this.dragonGroups = null;
        this.user = user;
    }
    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.dragons = null;
        this.commands = null;
        this.uniqueColors = null;
        this.dragonGroups = null;
        this.user = null;
    }

    public Vector<Dragon> getDragons() {
        return this.dragons;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, dragons);
    }

    @Override
    public String toString() {
        return "Response{" +
                "success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", dragonsCount='" + ((dragons == null) ? null : dragons.size()) + '\'' +
                '}';
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public Set<Color> getUniqueColors() {
        return uniqueColors;
    }

    public Map<LocalDateTime, List<Dragon>> getDragonGroups() {
        return dragonGroups;
    }

    public User getSession() {
        return user;
    }
}