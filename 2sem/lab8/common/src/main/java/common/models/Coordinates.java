package common.models;


import java.io.Serializable;

/**
 * Класс, представляющий координаты в двумерном пространстве.
 */
public class Coordinates implements Serializable {
    private int id;

    private double x;
    private Long y; // Поле не может быть null

    /**
     * Конструктор класса Coordinates, создает объект координат с указанными значениями.
     * @param x координата по оси X.
     * @param y координата по оси Y. Не может быть null.
     */
    public Coordinates(int id, double x, Long y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    /**
     * Валидация координат. Проверяет, что значение координаты Y не null.
     * @return true, если координаты валидны, иначе - false.
     */
    public boolean validate() {
        if (y == null) return false; // y не должен быть null
        return true;
    }

    /**
     * Возвращает строковое представление объекта в формате "x;y".
     * @return строковое представление координат.
     */
    @Override
    public String toString() {
        return x + ";" + y;
    }
}
