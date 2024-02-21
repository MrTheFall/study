package ru.ifmo.se.pokemon;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Stat.class */
public enum Stat {
    HP(false),
    ATTACK(false),
    DEFENSE(false),
    SPEED(false),
    SPECIAL_ATTACK(false),
    SPECIAL_DEFENSE(false),
    ACCURACY(true),
    EVASION(true);
    
    private boolean hidden;

    Stat(boolean z) {
        this.hidden = z;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}