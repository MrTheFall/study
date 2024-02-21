package ru.ifmo.se.pokemon;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/StatusMove.class */
public class StatusMove extends Move {
    public StatusMove() {
    }

    public StatusMove(Type type, double d, double d2) {
        super(type, d, d2);
    }

    public StatusMove(Type type, double d, double d2, int i, int i2) {
        super(type, d, d2, i, i2);
    }

    @Override // ru.ifmo.se.pokemon.Move
    public final void attack(Pokemon pokemon, Pokemon pokemon2) {
        for (int i = 0; i < this.hits; i++) {
            if (checkAccuracy(pokemon, pokemon2)) {
                System.out.println(pokemon + " " + describe() + ". ");
                if (this.type.getEffect(pokemon2.getTypes()) > 0.0d) {
                    applyOppEffects(pokemon2);
                } else {
                    System.out.println(pokemon2 + " " + Messages.get("noeffect") + " " + this.type);
                }
                if (this.type.getEffect(pokemon.getTypes()) > 0.0d) {
                    applySelfEffects(pokemon);
                }
            } else {
                System.out.println(pokemon + " " + Messages.get("miss"));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.ifmo.se.pokemon.Move
    public void applySelfEffects(Pokemon pokemon) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.ifmo.se.pokemon.Move
    public void applyOppEffects(Pokemon pokemon) {
    }
}