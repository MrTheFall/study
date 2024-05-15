package org.lab6.auth;
import common.models.User;

public class SessionHandler {
    public static User currentUser = null;

    public static User getSession() {
        return currentUser;
    }

    public static void setSession(User currentUser) {
        SessionHandler.currentUser = currentUser;
    }
}