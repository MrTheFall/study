package common.exceptions;

/**
 * Выбрасывается, если в ответе сервера ошибка
 */
public class APIException extends Exception {
    public APIException(String message) {
        super(message);
    }
}