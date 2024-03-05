package common.models;

import common.utils.Element;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс, представляющий дракона.
 */
public class Dragon extends Element implements Comparable<Dragon>, Serializable {
    private int id;
    private String name;
    private Coordinates coordinates;
    private final LocalDateTime creationDate; // Генерация даты создания
    private int age;
    private Boolean speaking;
    private Color color;
    private DragonCharacter character;
    private Person killer;

    /**
     * Конструктор класса Dragon.
     * @param id идентификатор дракона.
     * @param name имя дракона.
     * @param coordinates координаты дракона.
     * @param age возраст дракона.
     * @param speaking говорит ли дракон.
     * @param color цвет дракона.
     * @param character характер дракона.
     * @param killer убийца дракона.
     */
    public Dragon(int id, String name, Coordinates coordinates, int age, Boolean speaking, Color color, DragonCharacter character, Person killer) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now(); // Автоматическая генерация даты создания
        this.age = age;
        this.speaking = speaking;
        this.color = color;
        this.character = character;
        this.killer = killer;
    }


    /**
     * Возвращает ID дракона.
     * @return ID дракона.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Устанавливает ID дракона.
     * @param id новый идентификатор дракона.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Проверяет валидность данных дракона.
     * @return true, если данные валидны, иначе - false.
     */
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (age <= 0) return false;
        if (color == null) return false;
        if (killer != null && !killer.validate()) return false;
        // Остальные поля не требуют валидации в данном контексте
        return true;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getSpeaking() {
        return speaking;
    }

    public void setSpeaking(Boolean speaking) {
        this.speaking = speaking;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public Person getKiller() {
        return killer;
    }

    public void setKiller(Person killer) {
        this.killer = killer;
    }

    /**
     * Сравнивает текущего дракона с другим.
     * @param other другой дракон.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(Dragon other) {
        return Integer.compare(this.id, other.id);
    }

    /**
     * Проверяет равенство текущего дракона с другим объектом.
     * @param o другой объект.
     * @return true, если объекты равны, иначе - false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon that = (Dragon) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Возвращает хеш-код дракона.
     * @return хеш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, color);
    }


    /**
     * Возвращает строковое представление экземпляра класса Dragon.
     * @return строковое представление экземпляра класса Dragon.
     */
    @Override
    public String toString() {
        String info = "";
        info += "Дракон №" + id;
        info += " (добавлен " + creationDate.toString() + ")";
        info += "\n Имя: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Возраст: " + age;
        info += "\n Говорящий?: " +  ((speaking == null) ? null : "'" + speaking.toString() + "'");
        info += "\n Цвет: " + color;
        info += "\n Характер: " + character;
        info += "\n Убийца дракона:" + ((killer == null) ? null : killer.toString());
        return info;
    }
}