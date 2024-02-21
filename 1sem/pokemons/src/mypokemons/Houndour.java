package mypokemons;

import mymoves.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
public class Houndour extends Pokemon {
    public Houndour(String name, int level) {
        super(name, level);
        super.setType(Type.DARK, Type.FIRE);
        super.setStats(45,60, 30,80,50,65);
        super.setMove(new HydroPump(), new ThunderShock(), new Endeavor(), new Bite());
    }
}
