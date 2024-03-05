package org.lab6.models;


/**
 * Класс, представляющий координаты в двумерном пространстве.
 */
public class Coordinates {

    private double x;
    private Long y; // Поле не может быть null

    /**
     * Конструктор класса Coordinates, создает объект координат с указанными значениями.
     * @param x координата по оси X.
     * @param y координата по оси Y. Не может быть null.
     */
    public Coordinates(double x, Long y) {
        this.x = x;
        this.y = y;
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
