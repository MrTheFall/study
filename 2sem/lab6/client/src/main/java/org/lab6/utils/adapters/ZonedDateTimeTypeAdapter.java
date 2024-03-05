package org.lab6.utils.adapters;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Класс-адаптер для сериализации и десериализации объектов ZonedDateTime для библиотеки Gson.
 */
public class ZonedDateTimeTypeAdapter implements JsonDeserializer<ZonedDateTime>, JsonSerializer<ZonedDateTime> {

    /**
     * Десериализует JsonElement в объект ZonedDateTime.
     * @param json JsonElement, представляющий объект ZonedDateTime.
     * @param typeOfT тип объекта, в который нужно преобразовать json.
     * @param context контекст десериализации.
     * @return объект ZonedDateTime, полученный из json.
     * @throws JsonParseException если json невозможно преобразовать в ZonedDateTime.
     */
    @Override
    public ZonedDateTime deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
        try {
            String jsonStr = json.getAsJsonPrimitive().getAsString();
            return parseLocalDateTime(jsonStr);
        } catch (ParseException e) {
            throw new JsonParseException(e.getMessage(), e);
        }
    }

    /**
     * Парсит строку в объект ZonedDateTime.
     * @param dateString строка с датой и временем.
     * @return объект ZonedDateTime, полученный из строки.
     * @throws ParseException если строку не удается преобразовать в ZonedDateTime.
     */
    private ZonedDateTime parseLocalDateTime(final String dateString) throws ParseException {
        if (dateString != null && dateString.trim().length() > 0) {
            if (dateString.length() == 19) { // whithout zone
                LocalDateTime localDateTime = LocalDateTime.parse(dateString);
                return localDateTime.atZone(ZoneId.of("America/Sao_Paulo"));
//	    		return localDateTime.atZone(ZoneOffset.UTC);
            } else {
                return ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            }
        } else {
            return null;
        }
    }

    /**
     * Сериализует объект ZonedDateTime в JsonElement.
     * @param src объект ZonedDateTime для сериализации.
     * @param typeOfSrc тип исходного объекта.
     * @param context контекст сериализации.
     * @return JsonElement, представляющий объект src.
     */
    @Override
    public JsonElement serialize(final ZonedDateTime src, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        final String strDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                .withZone(ZoneId.of("America/Sao_Paulo"))
                .format(src);
        return new JsonPrimitive(strDateTime);
    }

}