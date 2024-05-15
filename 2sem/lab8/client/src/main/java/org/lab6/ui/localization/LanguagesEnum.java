package org.lab6.ui.localization;

public enum LanguagesEnum {
    RUSSIAN("RU", "ru"),
    DUTCH("NL", "nl"),
    FRENCH("FR", "fr"),
    SPANISH_NICARAGUA("NI", "es");


    private final String name;
    private final String languageName;

    LanguagesEnum(String name, String languageName) {
        this.name = name;
        this.languageName = languageName;
    }

    public String getName() {
        return name;
    }

    public String getLanguageName() {
        return languageName;
    }

    @Override
    public String toString() {
        return name;
    }
}