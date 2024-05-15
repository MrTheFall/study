package org.lab6.ui.controllers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import jdk.jshell.spi.ExecutionControl;
import org.lab6.auth.SessionHandler;
import org.lab6.network.TCPClient;
import org.lab6.ui.localization.LanguagesEnum;
import org.lab6.ui.GUIConfig;
import org.lab6.ui.models.AuthModel;
import org.lab6.utils.Runner;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthFormController extends AbstractController {


    private final AuthModel model;
    private ResourceBundle resourceBundle;
    @FXML
    private TextField loginUsernameInput;
    @FXML
    private TextField loginPasswordInput;
    @FXML
    private Button loginButton;
    @FXML
    private TextField registerUsernameInput;
    @FXML
    private TextField registerPasswordInput;
    @FXML
    private Button registerButton;

    @FXML
    private Menu languageMenu;
    @FXML
    private ChoiceBox<LanguagesEnum> languagesChoiceBox;
    private final ObservableList<LanguagesEnum> languages = FXCollections.observableArrayList(LanguagesEnum.values());

    public AuthFormController(TCPClient client){
        this.model = new AuthModel(client, this);
    }

    @FXML
    public void initialize() {
        languagesChoiceBox.setItems(languages);
        languagesChoiceBox.setOnAction(event -> {
            LanguagesEnum chosenLanguage = languagesChoiceBox.getValue();
            try {
                changeScene(GUIConfig.AUTH_FORM, controllerClass -> new AuthFormController(model.getConnectionHandler()), chosenLanguage);
            } catch (ExecutionControl.UserException e) {
                System.out.println(e);
            }
        });

        registerButton.setOnMouseClicked(mouseEvent -> {
            try {
                register();
            } catch (ExecutionControl.UserException e) {
                throw new RuntimeException(e);
            }
        });
        loginButton.setOnMouseClicked(mouseEvent -> {
            try {
                login();
            } catch (ExecutionControl.UserException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void register() throws ExecutionControl.UserException {
        registerButton.setDisable(true);
        boolean success = model.register(registerUsernameInput.getText(), registerPasswordInput.getText());
        if (success) {
            changeScene(GUIConfig.MENU_FORM, controllerClass -> new MenuFormController(model.getConnectionHandler()), getCurrentLocale());
        } else {
            registerButton.setDisable(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while registering");
            alert.setHeaderText("Error occurred while registering");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    @FXML
    private void login() throws ExecutionControl.UserException {
        loginButton.setDisable(true);
        boolean success = model.login(loginUsernameInput.getText(), loginPasswordInput.getText());
        if (success) {
            changeScene(GUIConfig.MENU_FORM, controllerClass -> new MenuFormController(model.getConnectionHandler()), getCurrentLocale());
        } else {
            loginButton.setDisable(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while authorizing");
            alert.setHeaderText("Error occurred while authorizing");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }
}