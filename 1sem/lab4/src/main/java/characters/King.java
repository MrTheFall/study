package characters;

public class King extends Character {
    public King(String name){
        super(name);
    }
    public void command(String command){
        System.out.println(getName() + " приказывает: " + command);
    }
}