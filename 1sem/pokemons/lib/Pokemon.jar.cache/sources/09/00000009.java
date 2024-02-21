package ru.ifmo.se.pokemon;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Move.class */
public abstract class Move {
    protected Type type;
    protected double power;
    protected double accuracy;
    protected int priority;
    protected int hits;
    private static final Move noMove = new Move(Type.NONE, 0.0d, 0.0d, -100, 0) { // from class: ru.ifmo.se.pokemon.Move.1
        @Override // ru.ifmo.se.pokemon.Move
        public final void attack(Pokemon pokemon, Pokemon pokemon2) {
        }

        @Override // ru.ifmo.se.pokemon.Move
        public String describe() {
            return Messages.get("noattack");
        }
    };
    private static final Move struggleMove = new PhysicalMove(Type.NONE, 50.0d, 1.0d) { // from class: ru.ifmo.se.pokemon.Move.2
        @Override // ru.ifmo.se.pokemon.Move
        public final String describe() {
            return Messages.get("struggle");
        }

        @Override // ru.ifmo.se.pokemon.DamageMove
        public final void applySelfDamage(Pokemon pokemon, double d) {
            pokemon.setMod(Stat.HP, (int) Math.round(d / 4.0d));
        }
    };
    private static final Move confusionMove = new PhysicalMove(Type.NONE, 40.0d, 1.0d) { // from class: ru.ifmo.se.pokemon.Move.3
        @Override // ru.ifmo.se.pokemon.Move
        public final String describe() {
            return Messages.get("confusion");
        }

        @Override // ru.ifmo.se.pokemon.DamageMove
        public final void applySelfDamage(Pokemon pokemon, double d) {
            pokemon.setMod(Stat.HP, (int) d);
        }

        @Override // ru.ifmo.se.pokemon.DamageMove
        public double calcCriticalHit(Pokemon pokemon, Pokemon pokemon2) {
            return 1.0d;
        }

        @Override // ru.ifmo.se.pokemon.DamageMove
        protected void applyOppDamage(Pokemon pokemon, double d) {
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void attack(Pokemon pokemon, Pokemon pokemon2);

    public Move() {
        this(Type.NONE, 0.0d, 1.0d, 0, 1);
    }

    public Move(Type type, double d, double d2) {
        this(type, d, d2, 0, 1);
    }

    public Move(Type type, double d, double d2, int i, int i2) {
        this.power = 0.0d;
        this.accuracy = 1.0d;
        this.priority = 0;
        this.hits = 1;
        this.type = type;
        this.accuracy = d2;
        this.power = d;
        this.priority = i;
        this.hits = i2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon2) {
        return (this.accuracy * pokemon.getStat(Stat.ACCURACY)) / pokemon2.getStat(Stat.EVASION) > Math.random();
    }

    public final int getPriority() {
        return this.priority;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String describe() {
        return Messages.get("attack");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void applyOppEffects(Pokemon pokemon) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void applySelfEffects(Pokemon pokemon) {
    }

    public static final Move getNoMove() {
        return noMove;
    }

    public static final Move getStruggleMove() {
        return struggleMove;
    }

    public static final Move getConfusionMove() {
        return confusionMove;
    }
}