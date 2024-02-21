package org.lab5.utils.adapters;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс-адаптер для сериализации и десериализации объектов LocalDateTime для библиотеки Gson.
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    /**
     * Сериализует объект LocalDateTime в JsonElement.
     * @param date объект LocalDateTime.
     * @param typeOfSrc тип исходного объекта.
     * @param context контекст сериализации.
     * @return JsonElement, представляющий объект date.
     */
    @Override
    public JsonElement serialize(LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // iso 6901
    }

    /**
     * Десериализует JsonElement в объект LocalDateTime.
     * @param json JsonElement, представляющий объект LocalDateTime.
     * @param type тип объекта, в который нужно преобразовать json.
     * @param context контекст десериализации.
     * @return объект LocalDateTime, полученный из json.
     * @throws JsonParseException если json невозможно преобразовать в LocalDateTime.
     */
    @Override
    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
    }
}