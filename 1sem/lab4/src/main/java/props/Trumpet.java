package props;

public class Trumpet extends Prop {
    public Trumpet(String name){
        super(name);
    }

    public void makeSound(){
        System.out.println(getName() + " издаёт звук");
    }
}
