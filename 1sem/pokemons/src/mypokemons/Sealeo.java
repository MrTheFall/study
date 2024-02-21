package mypokemons;

import mymoves.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Sealeo extends Pokemon {
    public Sealeo(String name, int level) {
        super(name, level);
        super.setType(Type.ICE, Type.GHOST);
        super.setStats(90, 60, 70, 75, 70, 45);
        super.setMove(new DoubleEdge(), new Endeavor(), new Substitute(), new TakeDown());
    }
}
