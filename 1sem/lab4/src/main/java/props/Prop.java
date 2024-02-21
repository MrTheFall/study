package props;

import food.*;
import interfaces.Nameable;

public abstract class Prop implements Nameable {
    protected String name;

    public Prop(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public void lookLike(String description) {
        System.out.println(getName() + " выглядит " + description);
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Prop prop = (Prop) obj;
        return name.equals(prop.name);
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "Prop{" +
                "name='" + name + '\'' +
                '}';
    }
}
