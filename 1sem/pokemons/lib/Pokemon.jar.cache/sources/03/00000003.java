package ru.ifmo.se.pokemon;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/DamageMove.class */
public abstract class DamageMove extends Move {
    protected abstract double calcAttDefFactor(Pokemon pokemon, Pokemon pokemon2);

    public DamageMove() {
    }

    public DamageMove(Type type, double d, double d2) {
        super(type, d, d2);
    }

    public DamageMove(Type type, double d, double d2, int i, int i2) {
        super(type, d, d2, i, i2);
    }

    protected double calcBaseDamage(Pokemon pokemon, Pokemon pokemon2) {
        return (((0.4d * pokemon.getLevel()) + 2.0d) * this.power) / 150.0d;
    }

    protected double calcTypeEffect(Pokemon pokemon, Pokemon pokemon2) {
        return this.type.getEffect(pokemon2.getTypes());
    }

    protected double calcCriticalHit(Pokemon pokemon, Pokemon pokemon2) {
        if (pokemon.getStat(Stat.SPEED) / 512.0d > Math.random()) {
            System.out.println(Messages.get("critical"));
            return 2.0d;
        }
        return 1.0d;
    }

    protected double calcSameTypeAttackBonus(Pokemon pokemon, Pokemon pokemon2) {
        double d = 1.0d;
        if (this.type != Type.NONE) {
            for (Type type : pokemon.getTypes()) {
                if (type == this.type) {
                    d *= 1.5d;
                }
            }
        }
        return d;
    }

    protected double calcRandomDamage(Pokemon pokemon, Pokemon pokemon2) {
        return Math.random() + 0.15d + 0.85d;
    }

    @Override // ru.ifmo.se.pokemon.Move
    public final void attack(Pokemon pokemon, Pokemon pokemon2) {
        for (int i = 0; i < this.hits; i++) {
            if (checkAccuracy(pokemon, pokemon2)) {
                System.out.println(pokemon + " " + describe() + ". ");
                double calcBaseDamage = ((calcBaseDamage(pokemon, pokemon2) * calcAttDefFactor(pokemon, pokemon2)) + 2.0d) * calcCriticalHit(pokemon, pokemon2) * calcSameTypeAttackBonus(pokemon, pokemon2) * calcRandomDamage(pokemon, pokemon2) * calcTypeEffect(pokemon, pokemon2);
                if (calcBaseDamage == 0.0d) {
                    calcBaseDamage = 1.0d;
                }
                double round = Math.round(calcBaseDamage);
                applyOppDamage(pokemon2, round);
                applySelfDamage(pokemon, round);
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

    protected void applyOppDamage(Pokemon pokemon, double d) {
        pokemon.setMod(Stat.HP, (int) Math.round(d));
    }

    protected void applySelfDamage(Pokemon pokemon, double d) {
    }
}