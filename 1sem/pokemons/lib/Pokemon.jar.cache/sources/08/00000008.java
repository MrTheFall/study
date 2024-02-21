package ru.ifmo.se.pokemon;

import java.util.Arrays;
import java.util.List;
import java.util.ListResourceBundle;

/* loaded from: Pokemon.jar:ru/ifmo/se/pokemon/Messages_ru_RU.class */
public class Messages_ru_RU extends ListResourceBundle {
    private static List<String> teams = Arrays.asList("красных", "белых", "желтых", "зеленых", "синих", "черных", "фиолетовых", "полосатых");
    private static Object[][] contents = {new Object[]{"poke", "Покемон"}, new Object[]{"noname", "Безымянный"}, new Object[]{"burn", "воспламеняется"}, new Object[]{"freeze", "замерзает"}, new Object[]{"thawn", "оттаивает"}, new Object[]{"paralyze", "парализован"}, new Object[]{"poison", "отравлен"}, new Object[]{"sleep", "засыпает"}, new Object[]{"faint", "теряет сознание."}, new Object[]{"bothFaint", "Оба покемона теряют сознание."}, new Object[]{"HP", "здоровья"}, new Object[]{"ATTACK", "атаку"}, new Object[]{"DEFENSE", "защиту"}, new Object[]{"SPEED", "скорость"}, new Object[]{"SPECIAL_ATTACK", "специальную атаку"}, new Object[]{"SPECIAL_DEFENSE", "специальную защиту"}, new Object[]{"ACCURACY", "точность"}, new Object[]{"EVASION", "уклоняемость"}, new Object[]{"minusHP", "теряет"}, new Object[]{"plusHP", "восстанавливает"}, new Object[]{"minusStat", "уменьшает"}, new Object[]{"plusStat", "увеличивает"}, new Object[]{"attack", "атакует"}, new Object[]{"struggle", "борется с соперником"}, new Object[]{"noattack", "ничего не делает"}, new Object[]{"confusion", "растерянно попадает по себе"}, new Object[]{"noeffect", "не замечает воздействие типа"}, new Object[]{"miss", "промахивается"}, new Object[]{"teams", teams}, new Object[]{"from", "из команды"}, new Object[]{"enter", "вступает в бой!"}, new Object[]{"tie", "В обеих командах закончились покемоны. Ничья!"}, new Object[]{"inTeam", "В команде"}, new Object[]{"empty", "не осталось покемонов."}, new Object[]{"team", "Команда"}, new Object[]{"wins", "побеждает в этом бою!"}, new Object[]{"critical", "Критический удар!"}};

    @Override // java.util.ListResourceBundle
    public Object[][] getContents() {
        return contents;
    }
}