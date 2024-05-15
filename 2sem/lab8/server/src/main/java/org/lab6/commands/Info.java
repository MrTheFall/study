package org.lab6.commands;

import common.network.Response;
import common.utils.AccessType;
import common.utils.ArgumentType;
import common.utils.Command;
import org.lab6.managers.CollectionManager;
import org.lab6.utils.console.Console;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private static final long serialVersionUID = 555644535434151212L;
    private final Console console;
    private final CollectionManager collectionManager;

    public Info(Console console, CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Map<ArgumentType, Object> args) {
        try {
            String infoString = "";
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
            infoString += "Сведения о коллекции:";
            infoString += "\n Тип: " + collectionManager.collectionType();
            infoString += "\n Количество элементов: " + collectionManager.collectionSize();
            infoString += "\n Дата последнего сохранения: " + lastSaveTimeString;
            infoString += "\n Дата последнего сохранения: " + lastInitTimeString;
            console.println("Отправлена информация о коллекции!");
            return new Response(true, infoString);
        } catch (Exception e) {
            console.println("Ошибка при отправке информации о коллекции!");
            return new Response(false, "Ошибка при отправке информации о коллекции");
        }
    }

    public ArrayList<ArgumentType> getArgumentType() {
        return new ArrayList<>(List.of(ArgumentType.AUTH_SESSION));
    }
    public AccessType getAccessType(){
        return AccessType.READ;
    }
}