package common.network;

import common.models.Dragon;

import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;

public class Response implements Serializable {
    private boolean success;
    private String message;
    private Vector<Dragon> dragons;

    public Response(boolean success, String message, Vector<Dragon> dragons) {
        this.success = success;
        this.message = message;
        this.dragons = dragons;
    }
    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.dragons = null;
    }

    public Vector<Dragon> getDragons() {
        return dragons;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, dragons);
    }

    @Override
    public String toString() {
        return "Response{" +
                "success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", dragons='" + dragons + '\'' +
                '}';
    }
}