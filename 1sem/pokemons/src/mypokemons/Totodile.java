package mypokemons;

import mymoves.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;


public class Totodile extends Pokemon {
    public Totodile(String name, int level) {
        super(name, level);
        super.setType(Type.WATER);
        super.setStats(50,65,64,44,48,43);
        super.setMove(new LightScreen(), new HydroPump(), new Charge(), new Meditate());
    }
}
