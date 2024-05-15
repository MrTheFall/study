package org.lab6.ui.models;

import common.models.User;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import common.utils.CommandDTO;
import jdk.jshell.spi.ExecutionControl;
import org.lab6.auth.SessionHandler;
import org.lab6.commands.Login;
import org.lab6.commands.Register;
import org.lab6.network.TCPClient;
import org.lab6.ui.controllers.AuthFormController;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;


public class AuthModel {
    private TCPClient connectionHandler;
    private String username;
    private String password;
    private final AuthFormController controller;

    public AuthModel(TCPClient connectionHandler, AuthFormController controller) {
        this.connectionHandler = connectionHandler;
        this.controller = controller;
    }

    public boolean register(String login, String password) throws ExecutionControl.UserException {
        Request request = new Request(new Register(), Map.of(ArgumentType.AUTH_SESSION, new User(login, password)));
        try {
            Response response = (Response) connectionHandler.sendAndReceiveCommand(request);
            if (response.isSuccess()){
                SessionHandler.setSession(new User(login, password));
                return true;
            }
            else {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean login(String login, String password) throws ExecutionControl.UserException {
        Request request = new Request(new Login(), Map.of(ArgumentType.AUTH_SESSION, new User(login, password)));
        try {
            Response response = (Response) connectionHandler.sendAndReceiveCommand(request);
            if (response.isSuccess()){
                SessionHandler.setSession(new User(login, password));
                return true;
            }
            else {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public TCPClient getConnectionHandler() {
        return connectionHandler;
    }
}