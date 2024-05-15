package org.lab6.ui.localization;


import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Localization {
    private final Locale currentLocale = Locale.getDefault();
    private final Locale ruRuLocale = new Locale("ru", "RU");
    private final Locale nlNlLocale = new Locale("nl", "NL");
    private final Locale frFrLocale = new Locale("fr", "FR");
    private final Locale esNiLocale = new Locale("es", "NI");
    private ResourceBundle resourceBundle;

    public Localization(LanguagesEnum lang) {
        try {
            switch (lang) {
                case DUTCH:
                    resourceBundle = ResourceBundle.getBundle("lang", nlNlLocale);
                    break;
                case FRENCH:
                    resourceBundle = ResourceBundle.getBundle("lang", frFrLocale);
                    break;
                case SPANISH_NICARAGUA:
                    resourceBundle = ResourceBundle.getBundle("lang", esNiLocale);
                    break;
                case RUSSIAN:
                default:
                    resourceBundle = ResourceBundle.getBundle("lang", ruRuLocale);
                    break;
            }
        } catch (MissingResourceException e) {
            resourceBundle = ResourceBundle.getBundle("lang", ruRuLocale);
        }
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
