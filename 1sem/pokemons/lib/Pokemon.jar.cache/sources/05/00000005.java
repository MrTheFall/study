package ru.ifmo.se.pokemon;

/* compiled from: Battle.java */
/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/FireMove.class */
class FireMove extends SpecialMove {
    public FireMove(Type type, double d, double d2) {
        super(type, d, d2);
    }

    @Override // ru.ifmo.se.pokemon.Move
    public void applyOppEffects(Pokemon pokemon) {
        pokemon.setCondition(new Effect().condition(Status.FREEZE).chance(0.8d).attack(0.0d).turns(-1));
    }
}