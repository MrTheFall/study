package mymoves;

import ru.ifmo.se.pokemon.*;
import utils.Utils;

public class Charge extends PhysicalMove {
    public Charge() {
        super(Type.ELECTRIC, 0, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().turns(5).stat(Stat.SPECIAL_DEFENSE, 1));
    }

    @Override
    protected String describe(){
        return "использует Charge";
    }
}
