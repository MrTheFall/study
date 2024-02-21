package org.lab5.commands;

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
