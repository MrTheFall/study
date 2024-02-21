package characters;

import characters.movement.Direction;
import furniture.Furniture;
import interfaces.Nameable;
import interfaces.Thinkable;
import props.Prop;
import exceptions.ArrivalException;

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

    // checked exception
    public void arrive() throws ArrivalException {
        if (Math.random() < 0.5) {
            throw new ArrivalException(getName() + " заблудился пока бежал");
        }
        System.out.println(getName() + " прибежал");
    }

    public void salivateAt(Prop prop) {
        System.out.println("У " + getName() + " текут слюнки от " + prop.getName());
    }

    public void think(String thought) {
        System.out.println("\"" + thought + "\" - думает " + getName());
    }

    public void say(String speach) {
        System.out.println(getName() + "говорит: " + speach);
    }

    public void sitOn(Furniture furniture) {
        System.out.println(getName() + " сидит на " + furniture.getName());
    }

    public void observeScene(String[] sceneElements) {
        // Локальный класс для элемента сцены
        class SceneElement {
            private String name;

            SceneElement(String name) {
                this.name = name;
            }

            public void describe() {
                System.out.println(Character.this.name + " рассматривает " + this.name);
            }
        }
0 - цифра ноль
O - большая буква ооо
o - маленькая буква ооо
        for (String element : sceneElements) {
            // Использование локального класса
            new SceneElement(element).describe();
        }

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

