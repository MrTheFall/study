package org.lab5.commands;

import org.lab5.utils.ExecutionResponse;


/**
 * Интерфейс выполняемых объектов.
 */
public interface Executable {
    /**
     * Выполняет что-либо.
     * @param arguments Аргумент для выполнения
     * @return результат выполнения
     */
    ExecutionResponse apply(String[] arguments);
}

