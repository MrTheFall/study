package org.lab5.models;

/**
 * Класс, представляющий человека.
 */
public class Person {

    private String name; // Поле не может быть null, строка не может быть пустой
    private java.time.ZonedDateTime birthday; // Поле может быть null
    private Float height; // Поле может быть null, значение должно быть больше 0
    private Long weight; // Поле может быть null, значение должно быть больше 0
    private Location location; // Поле может быть null

    /**
     * Конструктор класса Person, создает объект человека с указанными значениями.
     * @param name имя человека. Не может быть null или пустым.
     * @param birthday день рождения человека. Может быть null.
     * @param height рост человека. Может быть null, если задан, должен быть больше 0.
     * @param weight вес человека. Может быть null, если задан, должен быть больше 0.
     * @param location местоположение человека. Может быть null.
     */
    public Person(String name, java.time.ZonedDateTime birthday, Float height, Long weight, Location location) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.location = location;
    }

    /**
     * Валидация данных человека. Проверяет, что имя не null и не пустое, а также что рост и вес, если они заданы, больше 0.
     * @return true, если данные валидны, иначе - false.
     */
    public boolean validate() {
        if (name == null || name.isEmpty()) return false; // name не должно быть null или пустым
        if (height != null && height <= 0) return false; // height, если задано, должно быть больше 0
        if (weight != null && weight <= 0) return false; // weight, если задано, также должно быть больше 0
        // Проверка на location не требуется, так как оно может быть null
        return true;
    }

    /**
     * Возвращает строковое представление экземпляра класса Person.
     * @return строковое представление экземпляра класса Person.
     */
    @Override
    public String toString() {
        String info = "";
        info += "Имя: " + name;
        info += "\n Дата рождения: " + birthday;
        info += "\n Рост: " + height;
        info += "\n Вес: " + weight;
        info += "\n Местоположение: " + location;
        return info;
    }
}