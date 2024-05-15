package common.utils;

/**
 * Интерфейс объектов, имеющих описание.
 */
public interface Describable {
    /**
     * Получить имя.
     * @return имя
     */
    String getName();

    /**
     * Получить описание.
     * @return описание
     */
    String getDescription();
}
