package org.lab6.managers;

import common.models.Color;
import common.models.Dragon;
import org.lab6.utils.console.Console;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Менеджер коллекции.
 */
public class CollectionManager {
    private int currentId = 1;
    private Vector<Dragon> collection = new Vector<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DatabaseManager databaseManager;

    /**
     */
    public CollectionManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.lastInitTime = null;
        this.lastSaveTime = null;
    }

    /**
     * @return последнее время инициализации в формате LocalDateTime
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return последнее время сохранения в формате LocalDateTime
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return коллекция драконов.
     */
    public Vector<Dragon> getCollection() {
        return collection;
    }

    /**
     * @return Элемент коллекции по заданному ID
     */
    public Dragon getById(int id) {
        return collection.stream().filter(dragon -> dragon.getId() == id).findFirst().orElse(null);
    }

    /**
     * Проверяет, содержится ли переданный экземпляр дракона в коллекции. Проверка производится по id.
     * @return Boolean, есть ли дракон в коллекции.
     */
    public boolean contains(Dragon dragon) {
        return dragon == null || getById(dragon.getId()) != null;
    }

    /**
     * Получает незанятый id.
     * @return Свободный ID.
     */
    public int getFreeId() {
        while (getById(currentId) != null)
            if (++currentId < 0)
                currentId = 1;
        return currentId;
    }


    /**
     * Добавляет дракона в коллекцию на определенную позицию.
     * @param dragon дракон для добавления.
     * @param insertIndex позиция для добавления.
     * @return true, если дракон был успешно добавлен, иначе - false.
     */
    public boolean insert(Dragon dragon, int insertIndex) {
        if (contains(dragon)) return false;
        collection.insertElementAt(dragon, insertIndex);
        //update();
        return true;
    }


    /**
     * Добавляет дракона в коллекцию.
     * @param dragon дракон для добавления.
     * @return true, если дракон был успешно добавлен, иначе - false.
     */

    public boolean add(Dragon dragon) {
        if (contains(dragon)) return false;
        collection.add(dragon);
        update();
        return true;
    }

    /**
     * Обновляет существующего дракона в коллекции.
     * @param dragon дракон с обновленными данными.
     * @return true, если дракон был успешно обновлен, иначе - false.
     */
    public boolean updateExisting(Dragon dragon) {
        if (!contains(dragon)) return false;
        collection.removeIf(existingDragon -> existingDragon.getId() == dragon.getId());
        collection.add(dragon);
        update();
        return true;
    }

    /**
     * Удаляет дракона из коллекции по его идентификатору.
     * @param id идентификатор дракона для удаления.
     * @return true, если дракон был успешно удален, иначе - false.
     */
    public boolean remove(int id) {
        Dragon dragonToRemove = getById(id);
        if (dragonToRemove == null) return false;
        collection.remove(dragonToRemove);
        update();
        return true;
    }

    /**
     * Обновляет и сортирует коллекцию.
     */
    public void update() {
        this.sort(); // Dragon реализует Comparable<Dragon>
    }

    /**
     * Сортирует коллекцию.
     */
    public void sort() { Collections.sort(collection); }

    /**
     * Расчитывает средний возраст драконов в коллекции.
     * @return средний возраст драконов.
     */
    public double averageOfAge() {
        return collection.stream()
                .mapToInt(Dragon::getAge) // Допущение, что у класса Dragon есть метод getAge
                .average()                // Вычисляем среднее значение, результат OptionalDouble
                .orElse(Double.NaN);      // Возвращаем NaN, если коллекция пуста
    }

    /**
     * Группирует элементы коллекции по дате создания.
     * @return Строковое представление группировки.
     */
    public Map<LocalDateTime, List<Dragon>> groupCountingByCreationDate() {
        return collection.stream()
                .collect(Collectors.groupingBy(Dragon::getCreationDate));
    }

    /**
     * Возвращает уникальные цвета драконов в коллекции.
     * @return набор уникальных цветов драконов.
     */
    public Set<Color> getUniqueColors() {
        return collection.stream()
                .map(Dragon::getColor)
                .collect(Collectors.toSet());
    }

    /**
     * Инициализирует коллекцию, загружая данные из dumpManager.
     * @return true, если инициализация прошла успешно.
     */
    public boolean init() {
        collection.clear();
        collection = databaseManager.loadCollection();
        lastInitTime = LocalDateTime.now();
        for (Dragon dragon : collection) {
            int id = dragon.getId();
            if (id > currentId) {
                currentId = id;
            }
        }
        update();
        return true;
    }

    /**
     * Очищает коллекцию
     */
    public void clear() {
        collection.clear();
        currentId = 1;
    }

    /**
     * Проверяет валидность всех элементов коллекции. В случае обнаружения невалидного элемента выводит ошибку.
     * @param console консоль для вывода сообщений.
     */
    public void validateAll(Console console) {
        collection.forEach(dragon -> {
            if (!dragon.validate()) {
                console.printError("Дракон с id=" + dragon.getId() + " имеет невалидные поля.");
            }
        });
        console.println("Выполнена проверка корректности загруженных данных");
    }


    /**
     * @return Имя типа коллекции.
     */
    public String collectionType() {
        return collection.getClass().getName();
    }

    /**
     * @return Размер коллекции.
     */
    public int collectionSize() {
        return collection.size();
    }

    /**
     * Возвращает строковое представление коллекции.
     * @return строковое представление коллекции.
     */
    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        StringBuilder info = new StringBuilder();
        for (Dragon dragon : collection) {
            info.append(dragon).append("\n\n");
        }
        return info.toString().trim();
    }
}

