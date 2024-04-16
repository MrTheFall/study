package org.lab6.commands;

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
