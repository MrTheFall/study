package common.models;

import common.utils.Element;

import java.io.Serializable;

public class User implements Serializable {
    private final String username;
    private final String password;

    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public boolean validate() {
        return getUsername().length() < 40;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + username + '\'' +
                ", password='********'" +
                '}';
    }

}