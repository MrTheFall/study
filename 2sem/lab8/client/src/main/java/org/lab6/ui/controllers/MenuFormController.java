package org.lab6.ui.controllers;

import common.models.Dragon;
import common.utils.ArgumentType;
import common.utils.Command;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl;
import org.lab6.auth.SessionHandler;
import org.lab6.commands.*;
import org.lab6.network.TCPClient;
import org.lab6.ui.ChildUIType;
import org.lab6.ui.GUIConfig;
import org.lab6.ui.localization.LanguagesEnum;
import org.lab6.ui.localization.Localization;
import org.lab6.ui.models.AuthModel;
import org.lab6.ui.models.MenuModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MenuFormController extends AbstractController {
    private final MenuModel model;
    @FXML
    private Pane visualPane;

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonClear;
    @FXML
    private Button buttonAverageOfAge;
    @FXML
    private Button buttonInfo;
    @FXML
    private Button buttonUniqueColors;

    @FXML
    private Label menuUsernameLabel;

    @FXML
    private Button buttonTableView;
    @FXML
    private Button buttonGraphicsView;


    @FXML
    private TextArea terminal;
    private DataVisualizerController currentVisualizerController;



    private final ObservableList<LanguagesEnum> languages = FXCollections.observableArrayList(LanguagesEnum.values());

    public MenuFormController(TCPClient client){
        this.model = new MenuModel(client, this);
    }

    public void initialize() {
        menuUsernameLabel.setText(SessionHandler.getSession().getUsername());
        buttonTableView.setDisable(false);
        buttonGraphicsView.setDisable(false);
    }

    public void notifyDataChanged(List<Dragon> elementsToRemove, List<Dragon> elementsToAdd, List<Dragon> elementsToUpdate) {
        if (currentVisualizerController != null) {
            currentVisualizerController.updateInfo(elementsToRemove, elementsToAdd, elementsToUpdate);
        }
    }
    public TextArea getTerminal() {
        return terminal;
    }

    protected void loadUI(String uiPath, Pane targetPane, ChildUIType uiType) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(uiPath));
            Localization localization = new Localization(getCurrentLocale());
            loader.setResources(localization.getResourceBundle());
            if (uiType.equals(ChildUIType.VISUALISATION)) {
                targetPane.getChildren().clear();
                Parent parent = loader.load();
                currentVisualizerController = loader.getController();
                model.updateCollection();
                currentVisualizerController.setInfo(model.getCollection());
                currentVisualizerController.setParentController(this);
                targetPane.getChildren().add(parent);
            } else if (uiType.equals(ChildUIType.SIMPLE_CHILD_WINDOW)) {
                Parent parent = loader.load();
                ChildWindowController controller = loader.getController();
                controller.setMenuModel(model);
                Stage stage = new Stage();
                stage.setTitle(GUIConfig.TITLE);
                stage.setResizable(false);
                stage.setScene(new Scene(parent));
                stage.show();
            } else if (uiType.equals(ChildUIType.DRAGON_PROFILE_CHILD_WINDOW)) {
                loader.setControllerFactory(controllerClass -> new DragonProfileController(getSelectedDragonInVisualisationWindow()));
                Parent parent = loader.load();
                ChildWindowController controller = loader.getController();
                controller.setMenuModel(model);
                Stage stage = new Stage();
                stage.setTitle(GUIConfig.TITLE);
                stage.setResizable(false);
                stage.setScene(new Scene(parent));
                stage.show();
            }  else if (uiType.equals(ChildUIType.ON_MAIN_MENU)) {
                Parent parent = loader.load();
                ChildWindowController controller = loader.getController();
                controller.setMenuModel(model);
                targetPane.getChildren().add(parent);
                getStage().setResizable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addButtonClicked() {
        loadUI(GUIConfig.ADD_FORM_PATH, null, ChildUIType.SIMPLE_CHILD_WINDOW);
    }

    @FXML
    private void clearButtonClicked() {
        model.executeCommand(new Clear(), Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession()));
        model.updateCollection();
    }
    @FXML
    private void infoButtonClicked() {
        model.executeCommand(new Info(), Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession()));
    }
    @FXML
    private void uniqueColorsButtonClicked() {
        model.executeCommand(new PrintUniqueColor(), Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession()));
    }
    @FXML
    private void averageOfAgeButtonClicked() {
        model.executeCommand(new AverageOfAge(), Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession()));
    }

    @FXML
    private void groupCountingByCreationDateButtonClicked() {
        model.executeCommand(new GroupCountingByCreationDate(), Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession()));
    }

    @FXML
    private void tableButtonClicked() {
        loadUI(GUIConfig.TABLEVIEW_FORM, visualPane, ChildUIType.VISUALISATION);
        buttonGraphicsView.setDisable(false);
        buttonTableView.setDisable(true);
    }
    @FXML
    private void visualizeButtonClicked() {
        loadUI(GUIConfig.VISUAL_PANE_PATH, visualPane, ChildUIType.VISUALISATION);
        buttonTableView.setDisable(false);
        buttonGraphicsView.setDisable(true);
    }
    public DataVisualizerController getCurrentVisualizerController() {
        return currentVisualizerController;
    }
    public Dragon getSelectedDragonInVisualisationWindow() {
        return currentVisualizerController.getChosenDragon();
    }
    public void closeApplication() {
        Platform.exit();
    }
}
