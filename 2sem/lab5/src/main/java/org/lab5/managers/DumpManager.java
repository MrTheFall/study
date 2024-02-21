package org.lab5.managers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.lab5.models.Dragon;
import org.lab5.utils.adapters.LocalDateTimeAdapter;
import org.lab5.utils.adapters.ZonedDateTimeTypeAdapter;
import org.lab5.utils.console.Console;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Менеджер, отвечающий за работу с файлами.
 */
public class DumpManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
            .create();

    private final String fileName;
    private final Console console;

    /**
     * @param fileName
     * @param console
     */
    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    /**
     * Записывает коллекцию в файл.
     * @param collection коллекция
     */
    public void writeCollection(Collection<Dragon> collection) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(fileName)), StandardCharsets.UTF_8)) {
            writer.write(gson.toJson(collection));
            console.println("Коллекция успешна сохранена в файл!");
        } catch (IOException exception) {
            console.printError("Загрузочный файл не может быть открыт!");
        }
    }



    /**
     * Считывает коллекцию из файла.
     * @return Считанная коллекция
     */
    public Vector<Dragon> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var scanner = new Scanner(new File(fileName))) {
                var jsonString = new StringBuilder();

                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        jsonString.append(line);
                    }
                }

                if (jsonString.length() == 0) {
                    jsonString = new StringBuilder("[]");
                }

                Vector<Dragon> collection;

                collection = gson.fromJson(jsonString.toString(), new TypeToken<Vector<Dragon>>() {}.getType());
                System.out.println(gson.toJson(collection));

                console.println("Коллекция успешно загружена!");
                return collection;

            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                console.printError("Загрузочный файл пуст!");
            } catch (JsonParseException exception) {
                console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new Vector<>();
    }

}
