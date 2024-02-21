package food;

import interfaces.Nameable;
public abstract class Food implements Nameable {
    protected String name;
    public Food(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public int hashCode() {
        return name.hashCode();
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Food food = (Food) obj;
        return name.equals(food.name);
    }
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                '}';
    }
}
