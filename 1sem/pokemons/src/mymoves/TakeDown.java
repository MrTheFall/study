package mymoves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class TakeDown extends PhysicalMove {
    public TakeDown(){
        super(Type.NORMAL,90,85);
    }
    @Override
    protected void applySelfDamage(Pokemon p, double damage){
        p.setMod(Stat.HP,(int)(damage/4));
    }

    @Override
    protected String describe(){
        return "использовал Double-Edge";
    }
}
