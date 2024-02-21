package ru.ifmo.se.pokemon;

import java.util.Arrays;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Messages.class */
public class Messages extends ListResourceBundle {
    private static ResourceBundle bundle = getBundle("ru.ifmo.se.pokemon.Messages");
    private static List<String> teams = Arrays.asList("Red", "White", "Yellow", "Greren", "Blue", "Black", "Purple", "Striped");
    private static Object[][] contents = {new Object[]{"poke", "Pokemon"}, new Object[]{"noname", "Unnamed"}, new Object[]{"burn", "is burned"}, new Object[]{"freeze", "is frozen"}, new Object[]{"thawn", "thawns"}, new Object[]{"paralyze", "is paralyzed"}, new Object[]{"poison", "is poisoned"}, new Object[]{"sleep", "is sleeping"}, new Object[]{"faint", "faints."}, new Object[]{"bothFaint", "Both pokemons faint."}, new Object[]{"HP", "hit points"}, new Object[]{"ATTACK", "attack"}, new Object[]{"DEFENSE", "defense"}, new Object[]{"SPEED", "speed"}, new Object[]{"SPECIAL_ATTACK", "special attack"}, new Object[]{"SPECIAL_DEFENSE", "special defense"}, new Object[]{"ACCURACY", "accuracy"}, new Object[]{"EVASION", "evasion"}, new Object[]{"minusHP", "loses"}, new Object[]{"plusHP", "restores"}, new Object[]{"minusStat", "decreases"}, new Object[]{"plusStat", "increases"}, new Object[]{"attack", "attacks"}, new Object[]{"struggle", "struggles"}, new Object[]{"noattack", "does nothing"}, new Object[]{"confusion", "hits himself in confusion"}, new Object[]{"noeffect", "isn't affected by"}, new Object[]{"miss", "misses"}, new Object[]{"teams", teams}, new Object[]{"from", "from the team"}, new Object[]{"enter", "enters the battle!"}, new Object[]{"tie", "Both teams are out of Pokemons. It's a tie!"}, new Object[]{"inTeam", "Team"}, new Object[]{"empty", "loses its last Pokemon."}, new Object[]{"team", "The team"}, new Object[]{"wins", "wins the battle!"}, new Object[]{"critical", "Critical hit!"}};

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String get(String str) {
        return bundle.getString(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object getObj(String str) {
        return bundle.getObject(str);
    }

    @Override // java.util.ListResourceBundle
    public Object[][] getContents() {
        return contents;
    }
}