package characters;

import characters.movement.Direction;
import interfaces.Nameable;
import interfaces.Thinkable;
import props.Prop;

public abstract class Character implements Nameable, Thinkable {
    protected String name;
    private Prop objectInHand;

    public Character(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void standBy(Character character, Direction direction) {
        System.out.println(getName() + " находится " + direction.getDirection() + " " + character.getName());
    }

    public void salivateAt(Prop prop) {
        System.out.println("У " + getName() + " текут слюнки от " + prop.getName());
    }

    public void think(String thought) {
        System.out.println("\"" + thought + "\" - думает " + getName());
    }

    public void walkTo(Direction direction) {
        System.out.println(getName() + " идет " + direction.getDirection());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Character character = (Character) obj;
        return name.equals(character.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                '}';
    }

    public void hold(Prop object) {
        this.objectInHand = object;
        System.out.println(getName() + " держит " + this.objectInHand.getName());
    }
}

