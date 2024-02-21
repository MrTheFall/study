package ru.ifmo.se.pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Battle.class */
public final class Battle {
    private Team allies;
    private Team foes;
    private String allyName;
    private String foeName;

    public Battle() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll((List) Messages.getObj("teams"));
        Collections.shuffle(arrayList);
        this.allies = new Team((String) arrayList.get(0));
        this.foes = new Team((String) arrayList.get(1));
    }

    public void addAlly(Pokemon pokemon) {
        this.allies.add(pokemon);
    }

    public void addFoe(Pokemon pokemon) {
        this.foes.add(pokemon);
    }

    private boolean checkFirst(Pokemon pokemon, Pokemon pokemon2) {
        return pokemon.getPreparedMove().getPriority() == pokemon2.getPreparedMove().getPriority() ? pokemon.getStat(Stat.SPEED) == pokemon2.getStat(Stat.SPEED) ? Math.random() >= 0.5d : pokemon.getStat(Stat.SPEED) > pokemon2.getStat(Stat.SPEED) : pokemon.getPreparedMove().getPriority() > pokemon2.getPreparedMove().getPriority();
    }

    public void go() {
        do {
            this.allies.next();
            this.foes.next();
            do {
                this.allies.poke().prepareMove();
                this.foes.poke().prepareMove();
                Pokemon poke = checkFirst(this.allies.poke(), this.foes.poke()) ? this.allies.poke() : this.foes.poke();
                Pokemon poke2 = checkFirst(this.allies.poke(), this.foes.poke()) ? this.foes.poke() : this.allies.poke();
                if (poke.attack(poke2) || poke2.attack(poke)) {
                    break;
                }
                this.allies.poke().turn();
                if (!this.allies.poke().isAlive()) {
                    break;
                }
                this.foes.poke().turn();
            } while (this.foes.poke().isAlive());
            if (!this.allies.hasNext()) {
                break;
            }
        } while (this.foes.hasNext());
        if (!this.allies.hasNext() && !this.foes.hasNext()) {
            System.out.println(Messages.get("tie"));
            return;
        }
        String name = (this.allies.hasNext() ? this.allies : this.foes).getName();
        System.out.println(Messages.get("inTeam") + " " + (this.foes.hasNext() ? this.allies : this.foes).getName() + " " + Messages.get("empty"));
        System.out.println(Messages.get("team") + " " + name + " " + Messages.get("wins"));
    }

    public static void main(String[] strArr) {
        Battle battle = new Battle();
        battle.addAlly(new Pokemon("Весельчак У", 20) { // from class: ru.ifmo.se.pokemon.Battle.1
            {
                setStats(10.0d, 20.0d, 10.0d, 10.0d, 10.0d, 20.0d);
                setMove(new FireMove(Type.DRAGON, 5.0d, 1.0d));
            }
        });
        battle.addFoe(new Pokemon("Тутан Хамон", 25) { // from class: ru.ifmo.se.pokemon.Battle.2
            {
                setStats(10.0d, 10.0d, 20.0d, 10.0d, 10.0d, 10.0d);
                addMove(new FireMove(Type.DARK, 4.0d, 0.8d));
            }
        });
        battle.go();
    }
}