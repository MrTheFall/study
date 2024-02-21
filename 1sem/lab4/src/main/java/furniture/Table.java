package furniture;

import props.*;
public class Table extends Furniture {
    public Table(String name){
        super(name);
    }
    private Prop topObject;
    private Prop bottomObject;

    public void setTopObject(Prop object){
        this.topObject = object;
        System.out.println("На " + getName() + " стоит " + object.getName());
    }
    public void setBottomObject(Prop object){
        this.bottomObject = object;
        System.out.println("Под " + getName() + " стоит " + object.getName());
    }
}