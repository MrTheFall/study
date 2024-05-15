package org.lab6.ui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import jdk.jshell.spi.ExecutionControl;
import org.lab6.network.TCPClient;
import org.lab6.ui.GUIConfig;
import org.lab6.ui.localization.LanguagesEnum;
import org.lab6.ui.localization.Localization;
import org.lab6.utils.Runner;

import java.io.IOException;
import java.util.ResourceBundle;


public abstract class AbstractController {

    protected TCPClient client;
    private Stage currentStage;

    private ResourceBundle resourceBundle;
    private LanguagesEnum currentLocale;

    public void setStage(Stage stage) {
        currentStage = stage;
    }

    public Stage getStage() {
        return currentStage;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }


    public <T extends AbstractController> T changeScene(String resourcePath, Callback<Class<?>, Object> controllerConstructorCallback, LanguagesEnum lang) throws ExecutionControl.UserException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
        loader.setControllerFactory(controllerConstructorCallback);
        Localization localization = new Localization(lang);
        loader.setResources(localization.getResourceBundle());
        try {
            Parent parent = loader.load();
            T controller = loader.getController();
            controller.setResourceBundle(loader.getResources());
            controller.setCurrentLocale(lang);
            controller.setStage(getStage());
            Scene scene = new Scene(parent);
            currentStage.setTitle(GUIConfig.TITLE);
            currentStage.setScene(scene);
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExecutionControl.UserException(getResourceBundle().getString("new_window_opening_error"), null, null);
        }
    }
    public LanguagesEnum getCurrentLocale() {
        return currentLocale;
    }
    public void setCurrentLocale(LanguagesEnum currentLocale) {
        this.currentLocale = currentLocale;
    }
}