package mymoves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class DoubleEdge extends PhysicalMove{
    public DoubleEdge(){
        super(Type.NORMAL,120,100);
    }
    @Override
    protected void applySelfDamage(Pokemon p, double damage){
        p.setMod(Stat.HP,(int)(damage/3.0));
    }

    @Override
    protected String describe(){
        return "использовал Double-Edge";
    }
}

