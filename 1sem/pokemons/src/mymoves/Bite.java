package mymoves;

import ru.ifmo.se.pokemon.*;
import utils.Utils;

public class Bite extends PhysicalMove {

    public Bite() {
        super(Type.DARK, 60, 100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        super.applyOppDamage(def, damage);

        if(Utils.chance(0.3)) {
            Effect.flinch(def);
        }
    }

    @Override
    protected String describe(){
        return "использует Bite";
    }
}