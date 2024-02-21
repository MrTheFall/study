package mypokemons;

import mymoves.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Skarmory extends Pokemon {
    public Skarmory(String name, int level) {
        super(name, level);
        super.setType(Type.STEEL, Type.FLYING);
        super.setStats(65, 80, 140, 40, 70, 70);
        super.setMove(new HydroPump(), new ThunderShock(), new Endeavor());
    }
}
