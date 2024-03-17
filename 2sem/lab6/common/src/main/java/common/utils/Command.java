package common.utils;

import common.models.Dragon;
import common.network.Request;
import common.network.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/**
 * Абстрактная команда с именем и описанием
 */
public abstract class Command implements Describable, Executable, Serializable {
    private final String name;
    private final String description;

    /**
     * @param name
     * @param description
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return Название и использование команды.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Описание команды.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public abstract ArrayList<ArgumentType> getArgumentType();

    public abstract Response apply(Map<ArgumentType, Object> args);
}