package org.lab6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.lab6.network.TCPClient;
import org.lab6.ui.controllers.AbstractController;
import org.lab6.ui.controllers.AuthFormController;
import org.lab6.ui.localization.LanguagesEnum;
import org.lab6.ui.localization.Localization;
import org.lab6.utils.Runner;
import org.lab6.utils.console.StandardConsole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.lab6.ui.GUIConfig.*;

public class Main extends Application {

    private static final int PORT = 25565;
    public static final Logger logger = LogManager.getLogger("ClientLogger");
    private ResourceBundle resourceBundle;

    public static void main(String[] args) {
        launch(args); // This will start the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            var console = new StandardConsole();
            var client = new TCPClient(InetAddress.getLocalHost(), PORT);
            var runner = new Runner(console, client);

            // Set the default locale to Russian
//            Locale.setDefault(new Locale("ru", "RU"));
//            this.resourceBundle = ResourceBundle.getBundle("lang", Locale.getDefault());
//            // Load the login form
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forms/AuthForm.fxml"), this.resourceBundle);
//            Parent root = loader.load();
//
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.show();
            FXMLLoader mainWindowLoader = new FXMLLoader();
            mainWindowLoader.setLocation(getClass().getResource(AUTH_FORM));
            Localization localization = new Localization(LanguagesEnum.RUSSIAN);
            mainWindowLoader.setResources(localization.getResourceBundle());

            mainWindowLoader.setControllerFactory(c -> {
                AbstractController controller = new AuthFormController(client);
                controller.setCurrentLocale(LanguagesEnum.RUSSIAN);
                controller.setResourceBundle(localization.getResourceBundle());
                return controller;
            });

            Parent root = mainWindowLoader.load();
            AbstractController controller = mainWindowLoader.getController();
            controller.setStage(primaryStage);
            primaryStage.setTitle(TITLE);
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            


        } catch (ConnectException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No connection to server");
            alert.setHeaderText("No connection to server, app will be closed.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            //logger.error(resourceBundle.getString("server.unavailable"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
