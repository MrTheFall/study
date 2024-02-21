package ru.ifmo.se.pokemon;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/SpecialMove.class */
public class SpecialMove extends DamageMove {
    public SpecialMove() {
    }

    public SpecialMove(Type type, double d, double d2) {
        super(type, d, d2);
    }

    public SpecialMove(Type type, double d, double d2, int i, int i2) {
        super(type, d, d2, i, i2);
    }

    @Override // ru.ifmo.se.pokemon.DamageMove
    public final double calcAttDefFactor(Pokemon pokemon, Pokemon pokemon2) {
        return pokemon.getStat(Stat.SPECIAL_ATTACK) / pokemon2.getStat(Stat.SPECIAL_DEFENSE);
    }
}