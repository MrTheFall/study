package ru.ifmo.se.pokemon;

import java.util.LinkedList;
import java.util.Queue;

/* compiled from: Battle.java */
/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Team.class */
class Team {
    private Queue<Pokemon> team = new LinkedList();
    private Pokemon pokemon;
    private String name;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Team(String str) {
        this.name = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void add(Pokemon pokemon) {
        this.team.add(pokemon);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Pokemon next() {
        if (this.pokemon == null || !this.pokemon.isAlive()) {
            this.pokemon = this.team.poll();
            this.pokemon.restore();
            System.out.print(this.pokemon + " " + Messages.get("from") + " " + this.name);
            System.out.println(" " + Messages.get("enter"));
        }
        return this.pokemon;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasNext() {
        return !this.team.isEmpty() || this.pokemon.isAlive();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Pokemon poke() {
        return this.pokemon;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getName() {
        return this.name;
    }
}