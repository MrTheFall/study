package ru.ifmo.se.pokemon;

import java.util.LinkedList;
import java.util.List;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Pokemon.class */
public class Pokemon {
    private String name;
    private List<Type> types;
    private List<Move> moves;
    private Move preparedMove;
    private Effect stage;
    private Effect condition;
    private List<Effect> effects;
    private int confusion;
    private int level;
    private double[] base;

    public Pokemon(String str, int i) {
        this.types = new LinkedList();
        this.moves = new LinkedList();
        this.stage = new Effect();
        this.condition = new Effect();
        this.effects = new LinkedList();
        this.level = 1;
        this.base = new double[Stat.values().length];
        this.types.add(Type.NONE);
        this.moves.add(Move.getStruggleMove());
        this.name = str;
        setLevel(i);
    }

    public Pokemon() {
        this(Messages.get("noname"), 1);
    }

    public final void setStats(double d, double d2, double d3, double d4, double d5, double d6) {
        this.base[Stat.HP.ordinal()] = d;
        this.base[Stat.ATTACK.ordinal()] = d2;
        this.base[Stat.DEFENSE.ordinal()] = d3;
        this.base[Stat.SPECIAL_ATTACK.ordinal()] = d4;
        this.base[Stat.SPECIAL_DEFENSE.ordinal()] = d5;
        this.base[Stat.SPEED.ordinal()] = d6;
    }

    public final void setLevel(int i) {
        if (i < 1) {
            i = 1;
        }
        if (i > 100) {
            i = 100;
        }
        this.level = i;
    }

    public final double getStat(Stat stat) {
        double d = this.base[stat.ordinal()];
        double stat2 = this.stage.stat(stat) + (this.condition.success() ? this.condition.stat(stat) : 0.0d);
        for (Effect effect : this.effects) {
            stat2 += effect.success() ? effect.stat(stat) : 0.0d;
        }
        if (Math.abs(stat2) > 6.0d) {
            stat2 = stat2 > 0.0d ? 6.0d : -6.0d;
        }
        double d2 = stat.isHidden() ? 0.0d : stat == Stat.HP ? this.level + 10.0d : 5.0d;
        double d3 = stat.isHidden() ? 3.0d : 2.0d;
        double d4 = d * (stat == Stat.HP ? 1.0d : stat2 > 0.0d ? (d3 + stat2) / d3 : d3 / (d3 + stat2));
        return (stat.isHidden() ? d4 : ((((d4 * 2.0d) + 15.0d) + (Math.sqrt(0.0d) / 4.0d)) * this.level) / 100.0d) + d2;
    }

    public final boolean hasType(Type type) {
        for (Type type2 : this.types) {
            if (type2 == type) {
                return true;
            }
        }
        return false;
    }

    public final void addEffect(Effect effect) {
        if (effect.condition() == Status.NORMAL) {
            this.effects.add(effect);
        } else {
            setCondition(effect);
        }
    }

    public final void setCondition(Effect effect) {
        if (effect.success() && this.condition.condition() != effect.condition()) {
            this.condition = effect;
            String str = "";
            switch (effect.condition()) {
                case BURN:
                    str = Messages.get("burn");
                    break;
                case FREEZE:
                    str = Messages.get("freeze");
                    break;
                case PARALYZE:
                    str = Messages.get("paralyze");
                    break;
                case POISON:
                    str = Messages.get("poison");
                    break;
                case SLEEP:
                    str = Messages.get("sleep");
                    break;
            }
            System.out.println(this + " " + str);
        }
    }

    public final Status getCondition() {
        return this.condition.condition();
    }

    public final void confuse() {
        this.confusion = (int) ((Math.random() * 4.0d) + 1.0d);
    }

    public final void restore() {
        this.base[Stat.ACCURACY.ordinal()] = 1.0d;
        this.base[Stat.EVASION.ordinal()] = 1.0d;
        this.condition.clear();
        this.stage.clear();
        this.effects.clear();
    }

    public final double getHP() {
        return getStat(Stat.HP) - this.stage.stat(Stat.HP);
    }

    public final void setMod(Stat stat, int i) {
        String str;
        if (i != 0) {
            int stat2 = i + this.stage.stat(stat);
            if (stat == Stat.HP) {
                str = Messages.get(i > 0 ? "minusHP" : "plusHP") + " " + Math.abs(i);
            } else {
                if (Math.abs(stat2) > 6) {
                    stat2 = stat2 > 0 ? 6 : -6;
                }
                str = Messages.get(i < 0 ? "minusStat" : "plusStat");
            }
            this.stage.stat(stat, stat2);
            System.out.println(this + " " + str + " " + Messages.get(stat.toString()) + ".");
        }
    }

    public final Type[] getTypes() {
        return (Type[]) this.types.toArray(new Type[0]);
    }

    public final int getLevel() {
        return this.level;
    }

    private double getAttackChance() {
        double attack = this.stage.attack() * this.condition.attack();
        for (Effect effect : this.effects) {
            attack *= effect.attack();
        }
        return attack;
    }

    public final void prepareMove() {
        if (getAttackChance() > Math.random()) {
            if (this.moves.size() == 0) {
                this.preparedMove = Move.getStruggleMove();
                return;
            } else if (this.confusion > 0 && Math.random() < 0.33d) {
                this.preparedMove = Move.getConfusionMove();
                this.confusion--;
                return;
            } else {
                this.preparedMove = this.moves.get((int) Math.floor(Math.random() * this.moves.size()));
                return;
            }
        }
        this.preparedMove = Move.getNoMove();
    }

    public final boolean isAlive() {
        return getStat(Stat.HP) > ((double) this.stage.stat(Stat.HP));
    }

    public final boolean attack(Pokemon pokemon) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
        }
        this.preparedMove.attack(this, pokemon);
        if (isAlive() && pokemon.isAlive()) {
            System.out.println();
            return false;
        } else if (!isAlive() && !pokemon.isAlive()) {
            System.out.println(Messages.get("bothFaint"));
            return true;
        } else {
            System.out.println((isAlive() ? pokemon : this) + " " + Messages.get("faint"));
            return true;
        }
    }

    public final void turn() {
        setMod(Stat.HP, this.condition.stat(Stat.HP));
        if (this.condition.turn()) {
            this.condition.clear();
        }
        if (this.condition.condition() == Status.FREEZE && Math.random() < 0.2d) {
            this.condition.clear();
            System.out.println(this + " " + Messages.get("thawn"));
        }
        for (Effect effect : this.effects) {
            setMod(Stat.HP, effect.stat(Stat.HP));
            if (effect.turn()) {
                effect.clear();
            }
        }
    }

    protected final void setType(Type... typeArr) {
        this.types.clear();
        if (typeArr == null) {
            this.types.add(Type.NONE);
            return;
        }
        for (Type type : typeArr) {
            this.types.add(type);
            if (this.types.size() >= 2) {
                return;
            }
        }
    }

    protected final void addType(Type type) {
        if (this.types.size() < 2 && !this.types.contains(type)) {
            this.types.add(type);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setMove(Move... moveArr) {
        this.moves.clear();
        if (moveArr == null) {
            this.moves.add(Move.getStruggleMove());
            return;
        }
        for (Move move : moveArr) {
            this.moves.add(move);
            if (this.moves.size() >= 4) {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void addMove(Move move) {
        this.moves.add(move);
        while (this.moves.size() > 4) {
            this.moves.remove(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Move getPreparedMove() {
        return this.preparedMove;
    }

    public final String toString() {
        return (getClass().isAnonymousClass() ? Messages.get("poke") : getClass().getSimpleName()) + " " + this.name;
    }
}