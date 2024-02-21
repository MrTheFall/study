package ru.ifmo.se.pokemon;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Type.class */
public enum Type {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY,
    NONE;
    
    private static final int[][] effects = new int[values().length][values().length];

    static {
        NORMAL.half(ROCK, STEEL).zero(GHOST);
        FIRE.half(FIRE, WATER, ROCK, DRAGON).doub(GRASS, ICE, BUG, STEEL);
        WATER.doub(FIRE, GROUND, ROCK).half(WATER, GRASS, DRAGON);
        ELECTRIC.doub(WATER, FLYING).half(ELECTRIC, GRASS, DRAGON).zero(GROUND);
        GRASS.half(FIRE, GRASS, POISON, FLYING, BUG, DRAGON, STEEL).doub(WATER, GROUND, ROCK);
        ICE.half(FIRE, WATER, ICE, STEEL).doub(GRASS, GROUND, FLYING, DRAGON);
        FIGHTING.doub(NORMAL, ICE, ROCK, DARK, STEEL).half(POISON, FLYING, PSYCHIC, BUG, FAIRY).zero(GHOST);
        POISON.doub(GRASS, FAIRY).half(POISON, GROUND, ROCK, GHOST).zero(STEEL);
        GROUND.doub(FIRE, ELECTRIC, POISON, ROCK, STEEL).half(GRASS, BUG).zero(FLYING);
        FLYING.half(ELECTRIC, ROCK, STEEL).doub(GRASS, FIGHTING, BUG);
        PSYCHIC.doub(FIGHTING, POISON).half(PSYCHIC, STEEL).zero(DARK);
        BUG.half(FIRE, FIGHTING, POISON, FLYING, GHOST, STEEL, FAIRY).doub(GRASS, PSYCHIC, DARK);
        ROCK.doub(FIRE, ICE, FLYING, BUG).half(FIGHTING, GROUND, STEEL);
        GHOST.zero(NORMAL).doub(PSYCHIC, GHOST).half(DARK);
        DRAGON.doub(DRAGON).half(STEEL).zero(FAIRY);
        DARK.half(FIGHTING, DARK, FAIRY).doub(PSYCHIC, GHOST);
        STEEL.half(FIRE, WATER, ELECTRIC, STEEL, FAIRY).doub(ICE, ROCK);
        FAIRY.half(FIRE, POISON, STEEL).doub(FIGHTING, DRAGON, DARK);
    }

    private double effect(Type type) {
        return 1.0d - (effects[ordinal()][type.ordinal()] / 2.0d);
    }

    private Type setX(int i, Type... typeArr) {
        for (Type type : typeArr) {
            effects[ordinal()][type.ordinal()] = i;
        }
        return this;
    }

    private Type zero(Type... typeArr) {
        return setX(2, typeArr);
    }

    private Type half(Type... typeArr) {
        return setX(1, typeArr);
    }

    private Type doub(Type... typeArr) {
        return setX(-2, typeArr);
    }

    public double getEffect(Type... typeArr) {
        double d = 1.0d;
        for (Type type : typeArr) {
            d *= effect(type);
        }
        return d;
    }
}