package common.utils;

import common.models.Dragon;
import common.network.Request;
import common.network.Response;

import java.util.Map;
import java.util.Vector;


/**
 * Интерфейс выполняемых объектов.
 */
public interface Executable {
    /**
     * Выполняет что-либо.
     * @return результат выполнения
     */
    Response apply(Map<ArgumentType, Object> args);
}

