package mymoves;

import ru.ifmo.se.pokemon.*;
import utils.Utils;

public class LightScreen extends PhysicalMove {
    public LightScreen() {
        super(Type.PSYCHIC, 0, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().turns(5).stat(Stat.SPECIAL_DEFENSE, (int)p.getStat(Stat.SPECIAL_DEFENSE)/2));
    }

    @Override
    protected String describe(){
        return "использует Light Screen";
    }
}
