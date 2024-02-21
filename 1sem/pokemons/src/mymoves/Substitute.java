package mymoves;

import ru.ifmo.se.pokemon.*;

public class Substitute extends PhysicalMove {
    public Substitute(){
        super(Type.NORMAL,0,100);
    }

    @Override
    protected void applySelfDamage(Pokemon p, double damage) {
        double currSelfHp = p.getHP();
        p.setMod(Stat.HP,(int)(currSelfHp*(-2)));
    }

    @Override
    protected String describe(){
        return "использовал Substitute";
    }
}
