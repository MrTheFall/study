package food;

import interfaces.Nameable;

import javax.swing.*;

public class Pie extends Food {
    private String filling;
    public Pie(String name, String filling){
        super(name);
        this.filling = filling;
    }

    public String getFilling() {
        return this.filling;
    }
}