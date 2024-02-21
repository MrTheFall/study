package furniture;

import interfaces.Nameable;

public abstract class Furniture implements Nameable {
    protected String name;

    public Furniture(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Furniture furniture = (Furniture) obj;
        return name.equals(furniture.name);
    }

    public int hashCode() {

        return name.hashCode();
    }

    public String toString() {
        return "Furniture{" +
                "name='" + name + '\'' +
                '}';
    }
}