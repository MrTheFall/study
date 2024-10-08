package common.models;

import java.io.Serializable;

/**
 * Класс, представляющий местоположение в трехмерном пространстве.
 */
public class Location implements Serializable {

    private int id;
    private Float x; // Поле не может быть null
    private float y;
    private Long z; // Поле не может быть null

    /**
     * Конструктор класса Location, создает объект местоположения с указанными координатами.
     * @param x координата по оси X. Не может быть null.
     * @param y координата по оси Y.
     * @param z координата по оси Z. Не может быть null.
     */
    public Location(int id, Float x, float y, Long z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Валидация местоположения. Проверяет, что значения координат X и Z не null.
     * @return true, если местоположение валидно, иначе - false.
     */
    public boolean validate() {
        if (x == null) return false; // x не должно быть null
        if (z == null) return false; // z не должно быть null
        return true;
    }

    /**
     * Возвращает строковое представление экземпляра класса Location.
     * @return строковое представление экземпляра класса Location.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public Float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Long getZ() {
        return z;
    }

    public int getId() {
        return id;
    }
}
