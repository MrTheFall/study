package org.lab6.utils;

import org.lab6.models.*;
import org.lab6.utils.console.Console;

import java.util.NoSuchElementException;

/**
 * Класс для запроса данных от пользователя.
 */
public class Ask {
    // Исключение, выбрасываемое для выхода из процедуры ввода
    public static class AskBreak extends Exception {}

    /**
     * Запрашивает данные и создает экземпляр Dragon.
     * @param console консоль для ввода данных.
     * @param id идентификатор дракона.
     * @return объект Dragon с запрошенными данными.
     * @throws AskBreak если процесс ввода был прерван.
     */
    public static Dragon askDragon(Console console, int id) throws AskBreak {
        try {
            // Запрос имени дракона
            String name = askString(console, "Enter dragon's name: ", true);

            // Запрос координат дракона
            Coordinates coordinates = askCoordinates(console);

            // Запрос возраста дракона
            int age = askInt(console, "Enter dragon's age (>0): ", true);

            // Запрос говорящего дракона или нет
            Boolean speaking = askBoolean(console, "Is the dragon speaking? (true/false): ");

            // Запрос цвета дракона
            Color color = askColor(console);

            // Запрос характера дракона
            DragonCharacter character = askCharacter(console, "Enter dragon's character or leave blank if unknown (CUNNING/WISE/CHAOTIC_EVIL): ");

            // Запрос убийцы дракона (предполагаем, что для класса Person также реализован метод askPerson)
            Person killer = askPerson(console);

            return new Dragon(id, name, coordinates, age, speaking, color, character, killer);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("An error occurred while reading input.");
            return null;
        }
    }

    /**
     * Запрашивает строку от пользователя.
     * @param console консоль для ввода.
     * @param prompt приглашение к вводу.
     * @param nonEmpty должна ли строка быть непустой.
     * @return введенная строка.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static String askString(Console console, String prompt, boolean nonEmpty) throws AskBreak {
        String input;
        while (true) {
            console.print(prompt);
            input = console.readln().trim();
            if (input.equalsIgnoreCase("exit")) throw new AskBreak();
            if (nonEmpty && input.isEmpty()) {
                console.printError("The input cannot be empty. Please try again.");
            } else {
                break;
            }
        }
        return input;
    }


    /**
     * Запрашивает координаты от пользователя и создает объект Coordinates.
     * @param console консоль для ввода.
     * @return объект Coordinates с запрошенными координатами.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static Coordinates askCoordinates(Console console) throws AskBreak {
        double x;
        Long y;
        while (true) {
            x = askDouble(console, "Enter coordinate x: ", false);
            y = askLong(console, "Enter coordinate y (non-null): ", true);
            if (y != null) break;
        }
        return new Coordinates(x, y);
    }

    /**
     * Запрашивает целое число от пользователя.
     * @param console консоль для ввода.
     * @param prompt приглашение к вводу.
     * @param positiveOnly должно ли число быть положительным.
     * @return введенное целое число.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static int askInt(Console console, String prompt, boolean positiveOnly) throws AskBreak {
        int value;
        while (true) {
            try {
                value = Integer.parseInt(askString(console, prompt, true));
                if (positiveOnly && value <= 0) {
                    console.printError("The value must be greater than 0. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                console.printError("Invalid integer format. Please try again.");
            }
        }
        return value;
    }

    /**
     * Запрашивает булевое значение от пользователя.
     * @param console консоль для ввода.
     * @param prompt приглашение к вводу.
     * @return введенное булевое значение.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static double askDouble(Console console, String prompt, boolean positiveOnly) throws AskBreak {
        double value;
        while (true) {
            try {
                value = Double.parseDouble(askString(console, prompt, true));
                if (positiveOnly && value <= 0) {
                    console.printError("The value must be greater than 0. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                console.printError("Invalid double format. Please try again.");
            }
        }
        return value;
    }

    /**
     * Запрашивает длинное целое число от пользователя.
     * @param console консоль для ввода.
     * @param prompt приглашение к вводу.
     * @param nonNullOnly должно ли число быть не null.
     * @return введенное длинное целое число.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static Long askLong(Console console, String prompt, boolean nonNullOnly) throws AskBreak {
        Long value;
        while (true) {
            try {
                value = Long.valueOf(askString(console, prompt, nonNullOnly));
                break;
            } catch (NumberFormatException e) {
                if (nonNullOnly) {
                    console.printError("Invalid float format. Please try again.");
                } else {
                    return null;
                }
            }
        }
        return value;
    }

    /**
     * Запрашивает булевое значение от пользователя.
     * @param console консоль для ввода.
     * @param prompt приглашение к вводу.
     * @return введенное булевое значение.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static Boolean askBoolean(Console console, String prompt) throws AskBreak {
        Boolean value;
        while (true) {
            String input = askString(console, prompt, false);
            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes")) {
                value = true;
                break;
            } else if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no")) {
                value = false;
                break;
            } else if (input.isEmpty()) {
                value = null;
                break;
            } else {
                console.printError("Invalid boolean format. Please enter true/false or yes/no.");
            }
        }
        return value;
    }

    /**
     * Запрашивает цвет от пользователя и создает объект Color.
     * @param console консоль для ввода.
     * @return объект Color с запрошенным цветом.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static Color askColor(Console console) throws AskBreak {
        Color color;
        while (true) {
            String input = askString(console, "Enter dragon's color (RED/YELLOW/ORANGE): ", true);
            try {
                color = Color.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                console.printError("Invalid color. Please choose from RED, YELLOW, or ORANGE.");
            }
        }
        return color;
    }

    /**
     * Запрашивает характер дракона от пользователя и создает объект DragonCharacter.
     * @param console консоль для ввода.
     * @param prompt приглашение к вводу.
     * @return объект DragonCharacter с запрошенным характером.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static DragonCharacter askCharacter(Console console, String prompt) throws AskBreak {
        DragonCharacter character = null;
        String input;
        while (true) {
            console.println(prompt);
            input = console.readln().trim();
            if (input.equalsIgnoreCase("exit")) throw new AskBreak();
            if (!input.isEmpty()) {
                try {
                    character = DragonCharacter.valueOf(input.toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    console.printError("Invalid character. Please choose from CUNNING/WISE/CHAOTIC_EVIL");
                }
            } else {
                break;
            }
        }


        return character;
    }

    /**
     * Запрашивает информацию о человеке от пользователя и создает объект Person.
     * @param console консоль для ввода.
     * @return объект Person с запрошенной информацией.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static Person askPerson(Console console) throws AskBreak {
        String name;
        java.time.ZonedDateTime birthday;
        Float height;
        Long weight;
        Location location;

        console.println("Enter dragon's killer information or leave blank if unknown:");
        name = askString(console, "Killer's name: ", false);
        if (name.isEmpty()) return null; // предполагаем, что убийца может быть не указан

        birthday = askDateTime(console, "Killer's birthday (enter in format: YYYY-MM-DDThh:mm:ss+HH:MM) or leave blank: ");
        height = askFloat(console, "Killer's height (>0) or leave blank: ", true, false);
        weight = askLong(console, "Killer's weight (>0) or leave blank: ", false);
        location = askLocation(console); // предполагаем, что реализован метод askLocation

        return new Person(name, birthday, height, weight, location);
    }

    /**
     * Запрашивает дату и время от пользователя и создает объект ZonedDateTime.
     * @param console консоль для ввода.
     * @param prompt приглашение к вводу.
     * @return объект ZonedDateTime с запрошенной датой и временем.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static java.time.ZonedDateTime askDateTime(Console console, String prompt) throws AskBreak {
        java.time.ZonedDateTime dateTime = null;
        while (true) {
            String input = askString(console, prompt, false);
            if (input.isEmpty()) {
                return null;
            }
            try {
                dateTime = java.time.ZonedDateTime.parse(input); // Предполагаем стандартный формат ISO 8601
                break;
            } catch (java.time.format.DateTimeParseException e) {
                // 2024-02-07T16:15:24+00:00
                console.printError("Invalid date-time format. Please use format: YYYY-MM-DDThh:mm:ss+HH:MM");
            }
        }
        return dateTime;
    }

    /**
     * Запрашивает число с плавающей точкой от пользователя.
     * @param console консоль для ввода.
     * @param prompt приглашение к вводу.
     * @param positiveOnly должно ли число быть положительным.
     * @return введенное число с плавающей точкой.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static Float askFloat(Console console, String prompt, boolean positiveOnly, boolean nonEmpty) throws AskBreak {
        Float value = null;
        while (true) {
            String input = askString(console, prompt, nonEmpty);
            if (input.isEmpty()) {
                return null;
            }
            try {
                value = Float.parseFloat(input);
                if (positiveOnly && value <= 0) {
                    console.printError("The value must be greater than 0. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                console.printError("Invalid float format. Please try again.");
            }
        }
        return value;
    }

    /**
     * Запрашивает у пользователя данные о местоположении и создает объект Location.
     * @param console консоль для ввода данных.
     * @return объект Location с запрошенными данными.
     * @throws AskBreak если процесс ввода был прерван.
     */
    private static Location askLocation(Console console) throws AskBreak {
        console.println("Enter location details:");
        Float x = askFloat(console, "Location.x (non-null): ", false, true);
        float y = (float) askDouble(console, "Location.y (non-null): ", false); // y - примитивный тип, null не допускается
        Long z = askLong(console, "Location.z (non-null): ", true);

        if (x == null || z == null) {
            console.printError("Location coordinates cannot be null. Please enter valid values.");
            throw new AskBreak();
        }

        return new Location(x, y, z);
    }
}
