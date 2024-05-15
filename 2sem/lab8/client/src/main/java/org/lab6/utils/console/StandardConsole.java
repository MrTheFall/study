package org.lab6.utils.console;

import java.io.FileOutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс, реализующий интерфейс Console для работы с консолью.
 */
public class StandardConsole implements Console {
    private static final String PROMPT = "$ ";

    private static Scanner fileScanner = null;
    private static Scanner defScanner = new Scanner(System.in);

    private FileOutputStream fileOutputStream;

    /**
     * Выводит объект на консоль без перехода на новую строку.
     * @param obj объект для вывода.
     */
    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Выводит объект на консоль с переходом на новую строку.
     * @param obj объект для вывода.
     */
    @Override
        public void println(Object obj) {
            System.out.println(obj);
        }

    /**
     * Выводит сообщение об ошибке на консоль.
     * @param obj сообщение об ошибке.
     */
    @Override
    public void printError(Object obj) {
        System.err.println("Error: " + obj);
    }

    /**
     * Читает строку из консоли.
     * @return строка, считанная с консоли.
     * @throws NoSuchElementException если нет элементов для чтения.
     * @throws IllegalStateException если сканер закрыт.
     */
    @Override
    public String readln() throws NoSuchElementException, IllegalStateException {
        return (fileScanner!=null?fileScanner:defScanner).nextLine();
    }

    /**
     * Проверяет, возможно ли считывание строки из консоли.
     * @return true, если считывание строки возможно, иначе - false.
     * @throws IllegalStateException если сканер закрыт.
     */
    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner!=null?fileScanner:defScanner).hasNextLine();
    }

    /**
     * Выводит на консоль таблицу из двух объектов.
     * @param elementLeft первый объект таблицы.
     * @param elementRight второй объект таблицы.
     */
    @Override
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    /**
     * Отображает приглашение к вводу на консоли.
     */
    @Override
    public void prompt() {
        print(PROMPT);
    }

    /**
     * Возвращает текущее приглашение к вводу.
     * @return строка приглашения к вводу.
     */
    @Override
    public String getPrompt() {
        return PROMPT;
    }

    /**
     * Выбирает сканер для чтения из файла.
     * @param scanner сканер для чтения из файла.
     */
    @Override
    public void selectFileScanner(Scanner scanner) {
        this.fileScanner = scanner;
    }

    /**
     * Выбирает сканер для чтения из консоли.
     */
    @Override
    public void selectConsoleScanner() {
        this.fileScanner = null;
    }
}

