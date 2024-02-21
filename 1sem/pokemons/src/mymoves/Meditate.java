package mymoves;

import ru.ifmo.se.pokemon.*;

public class Meditate extends PhysicalMove {
    static {

    }
    public Meditate() {
        super(Type.PSYCHIC, 0, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().turns(5).stat(Stat.ATTACK, 1));
    }

    @Override
    protected String describe(){
        return "использует Meditate";
    }
}
