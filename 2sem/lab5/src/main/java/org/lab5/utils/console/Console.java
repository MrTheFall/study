package org.lab5.utils.console;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Интерфейс для работы с консолью.
 */
public interface Console {

    /**
     * Выводит объект на консоль без перехода на новую строку.
     * @param obj объект для вывода.
     */
    void print(Object obj);

    /**
     * Выводит объект на консоль с переходом на новую строку.
     * @param obj объект для вывода.
     */
    void println(Object obj);

    /**
     * Читает строку из консоли.
     * @return строка, считанная с консоли.
     */
    String readln();

    /**
     * Проверяет, возможно ли считывание строки из консоли.
     * @return true, если считывание строки возможно, иначе - false.
     */
    boolean isCanReadln();

    /**
     * Выводит сообщение об ошибке на консоль.
     * @param obj сообщение об ошибке.
     */
    void printError(Object obj);

    /**
     * Выводит на консоль таблицу из двух объектов.
     * @param obj1 первый объект таблицы.
     * @param obj2 второй объект таблицы.
     */
    void printTable(Object obj1, Object obj2);

    /**
     * Отображает приглашение к вводу на консоли.
     */
    void prompt();

    /**
     * Возвращает текущее приглашение к вводу.
     * @return строка приглашения к вводу.
     */
    String getPrompt();

    /**
     * Выбирает Scanner для чтения из файла.
     * @param obj объект Scanner для чтения из файла.
     */
    void selectFileScanner(Scanner obj);

    /**
     * Выбирает Scanner для чтения из консоли.
     */
    void selectConsoleScanner();
}

