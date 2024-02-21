package characters;

import props.Prop;

public class WhiteRabbit extends Character {
    private Prop objectInHand;
    public WhiteRabbit(String name){
        super(name);
    }
    @Override
    public void hold(Prop object){
        this.objectInHand = object;
        System.out.println(getName() + " держит в лапке " + this.objectInHand.getName());
    }
}