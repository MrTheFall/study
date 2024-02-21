package rooms;

import furniture.*;

public class Courtroom extends Room {
    public Courtroom(String name) {
        super(name);
    }

    public void setCenterObject(Table object){
        System.out.println("В центре " + getName() + " стоит " + object.getName());
    }


}