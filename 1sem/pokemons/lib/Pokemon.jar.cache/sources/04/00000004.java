package ru.ifmo.se.pokemon;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Effect.class */
public final class Effect {
    private int[] mods = new int[Stat.values().length];
    private int turns = 0;
    private double effectChance = 1.0d;
    private double attackChance = 1.0d;
    private Status condition = Status.NORMAL;

    public final Effect turns(int i) {
        this.turns = i;
        return this;
    }

    public final Effect chance(double d) {
        this.effectChance = d;
        return this;
    }

    public final Effect attack(double d) {
        this.attackChance = d;
        return this;
    }

    public final double attack() {
        return this.attackChance;
    }

    public final Effect condition(Status status) {
        this.condition = status;
        return this;
    }

    public final void clear() {
        for (Stat stat : Stat.values()) {
            this.mods[stat.ordinal()] = 0;
        }
        this.condition = Status.NORMAL;
        this.turns = 0;
        this.effectChance = 1.0d;
        this.attackChance = 1.0d;
    }

    public final Status condition() {
        return this.condition;
    }

    public final int stat(Stat stat) {
        return this.mods[stat.ordinal()];
    }

    public final Effect stat(Stat stat, int i) {
        if (stat != Stat.HP) {
            if ((i >= 0) & (i > 6)) {
                i = 6;
            }
            if ((i < 0) & (i < -6)) {
                i = -6;
            }
        }
        this.mods[stat.ordinal()] = i;
        return this;
    }

    public final boolean success() {
        return this.effectChance > Math.random();
    }

    public final boolean immediate() {
        return this.turns == 0;
    }

    public final boolean turn() {
        int i = this.turns - 1;
        this.turns = i;
        return i == 0;
    }

    public static void burn(Pokemon pokemon) {
        if (!pokemon.hasType(Type.FIRE)) {
            Effect turns = new Effect().condition(Status.BURN).turns(-1);
            turns.stat(Stat.ATTACK, -2).stat(Stat.HP, ((int) pokemon.getStat(Stat.HP)) / 16);
            pokemon.setCondition(turns);
        }
    }

    public static void paralyze(Pokemon pokemon) {
        if (!pokemon.hasType(Type.ELECTRIC)) {
            Effect turns = new Effect().condition(Status.PARALYZE).attack(0.75d).turns(-1);
            turns.stat(Stat.SPEED, -2);
            pokemon.setCondition(turns);
        }
    }

    public static void freeze(Pokemon pokemon) {
        if (!pokemon.hasType(Type.ICE)) {
            pokemon.setCondition(new Effect().condition(Status.FREEZE).attack(0.0d).turns(-1));
        }
    }

    public static void poison(Pokemon pokemon) {
        if (!pokemon.hasType(Type.POISON) && !pokemon.hasType(Type.STEEL)) {
            Effect turns = new Effect().condition(Status.POISON).turns(-1);
            turns.stat(Stat.HP, ((int) pokemon.getStat(Stat.HP)) / 8);
            pokemon.setCondition(turns);
        }
    }

    public static void sleep(Pokemon pokemon) {
        pokemon.setCondition(new Effect().condition(Status.SLEEP).attack(0.0d).turns((int) ((Math.random() * 3.0d) + 1.0d)));
    }

    public static void flinch(Pokemon pokemon) {
        pokemon.addEffect(new Effect().attack(0.0d).turns((int) ((Math.random() * 4.0d) + 1.0d)));
    }

    public static void confuse(Pokemon pokemon) {
        pokemon.confuse();
    }
}