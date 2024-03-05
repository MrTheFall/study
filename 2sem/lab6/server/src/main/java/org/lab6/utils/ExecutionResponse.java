package org.lab6.utils;

/**
 * Класс, представляющий ответ на выполнение команды.
 */
public class ExecutionResponse {
    private boolean exitCode;
    private String message;

    /**
     * Конструктор класса ExecutionResponse.
     * @param code код завершения команды.
     * @param s сообщение об результате выполнения команды.
     */
    public ExecutionResponse(boolean code, String s) {
        exitCode = code;
        message = s;
    }

    /**
     * Конструктор класса ExecutionResponse, создает успешный ответ с указанным сообщением.
     * @param s сообщение об успешном выполнении команды.
     */
    public ExecutionResponse(String s) {
        this(true, s);
    }

    /**
     * Возвращает код завершения команды.
     * @return код завершения команды.
     */
    public boolean getExitCode() {
        return exitCode;
    }

    /**
     * Возвращает сообщение об результате выполнения команды.
     * @return сообщение об результате выполнения команды.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Возвращает строковое представление объекта.
     * @return строковое представление объекта в формате "exitCode;message;".
     */
    public String toString() { return String.valueOf(exitCode)+";"+message+";"; }
}
