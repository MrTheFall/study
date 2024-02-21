package exceptions;

public class CantAttendException extends RuntimeException{
    public CantAttendException(String message) {
        super(message);
    }
}
