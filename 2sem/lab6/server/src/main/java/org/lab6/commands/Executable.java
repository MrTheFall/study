package org.lab6.commands;

import common.network.requests.Request;
import common.network.responses.Response;


/**
 * Интерфейс выполняемых объектов.
 */
public interface Executable {
    /**
     * Выполняет что-либо.
     * @param arguments Аргумент для выполнения
     * @return результат выполнения
     */
    Response apply(Request arguments);
}

