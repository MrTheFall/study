package ru.ifmo.se.pokemon;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/PhysicalMove.class */
public class PhysicalMove extends DamageMove {
    public PhysicalMove() {
    }

    public PhysicalMove(Type type, double d, double d2) {
        super(type, d, d2);
    }

    public PhysicalMove(Type type, double d, double d2, int i, int i2) {
        super(type, d, d2, i, i2);
    }

    @Override // ru.ifmo.se.pokemon.DamageMove
    protected final double calcAttDefFactor(Pokemon pokemon, Pokemon pokemon2) {
        return pokemon.getStat(Stat.ATTACK) / pokemon2.getStat(Stat.DEFENSE);
    }
}