package mymoves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Endeavor extends PhysicalMove {
    public Endeavor() {
        super(Type.NORMAL,0,100);
    }

    @Override
    protected double calcBaseDamage(Pokemon att, Pokemon def) {
        double attHP = att.getHP();
        double defHP = def.getHP();
        if (defHP > attHP) {
            def.setMod(Stat.HP, (int)(defHP-attHP));
        }
        return 0.0;
    }

    @Override
    protected String describe(){
        return "использует Endeavor";
    }
}
