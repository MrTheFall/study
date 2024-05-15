package org.lab6.ui.controllers;

import common.models.Color;
import common.models.Dragon;
import common.models.DragonCharacter;
import common.utils.ArgumentType;
import common.utils.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.Event;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl;
import org.lab6.auth.SessionHandler;
import org.lab6.commands.RemoveById;
import org.lab6.ui.models.DragonProfileModel;

import java.awt.*;
import java.util.Map;

public class DragonProfileController extends ChildWindowController {

    private final DragonProfileModel model;
    @FXML
    private Label nameValue, xValue, yValue, zValue, ageValue, speakingValue, colorValue, characterValue, killerValue, killerNameValue, killerBirthdayValue, killerHeightValue, killerWeightValue, killerXValue, killerYValue, killerZValue, authorValue, creationDateValue;
    @FXML
    private TextField name, x, y, z, age, killerName, killerBirthday, killerHeight, killerWeight, killerX, killerY, killerZ;
    @FXML
    private CheckBox speaking, killer;
    @FXML
    private ChoiceBox<Color> color;
    @FXML
    private ChoiceBox<DragonCharacter> character;
    @FXML
    private VBox updateVBox, valuesVBox;
    @FXML
    private Button updateButton, removeButton;
    @FXML
    private Pane topPane;
    private final ObservableList<common.models.Color> colors = FXCollections.observableArrayList(Color.values());
    private final ObservableList<DragonCharacter> characters = FXCollections.observableArrayList(DragonCharacter.values());
    private boolean updateOpened = false;


    public DragonProfileController(Dragon currentDragon) {
        model = new DragonProfileModel(this, currentDragon);
    }

    @FXML
    private void initialize() {
        bindProperties();
        color.setItems(colors);
        character.setItems(characters);
        topPane.setStyle("-fx-background-color:" + model.getCurrentDragon().getColor().toString());
        updateVBox.setVisible(false);
        valuesVBox.setVisible(true);
        if (model.getCurrentDragon().getOwnerUsername().equals(SessionHandler.getSession().getUsername())) {
            updateButton.setDisable(false);
            removeButton.setDisable(false);
        } else {
            updateButton.setDisable(true);
            removeButton.setDisable(true);
        }
        setTextFieldValues();
    }

    private void setTextFieldValues() {
        nameValue.setText(model.getCurrentDragon().getName());
        creationDateValue.setText(String.valueOf(model.getCurrentDragon().getCreationDate()));
        xValue.setText(String.valueOf(model.getCurrentDragon().getCoordinates().getX()));
        yValue.setText(String.valueOf(model.getCurrentDragon().getCoordinates().getY()));
        ageValue.setText(String.valueOf(model.getCurrentDragon().getAge()));
        speakingValue.setText(model.getCurrentDragon().getSpeaking().toString());
        colorValue.setText(model.getCurrentDragon().getColor().toString());
        characterValue.setText(model.getCurrentDragon().getCharacter() == null ? "-" : model.getCurrentDragon().getCharacter().toString());
        if (model.getCurrentDragon().getKiller() != null) {
            killerValue.setText("✔");
            killerNameValue.setText(model.getCurrentDragon().getKiller().getName() == null ? "-" : model.getCurrentDragon().getKiller().getName());
            killerBirthdayValue.setText(model.getCurrentDragon().getKiller().getBirthday() == null ? "-" : model.getCurrentDragon().getKiller().getBirthday().toString());
            killerHeightValue.setText(model.getCurrentDragon().getKiller().getHeight() == null ? "-" : model.getCurrentDragon().getKiller().getHeight().toString());
            killerWeightValue.setText(model.getCurrentDragon().getKiller().getWeight() == null ? "-" : model.getCurrentDragon().getKiller().getWeight().toString());
            killerXValue.setText(model.getCurrentDragon().getKiller().getLocation().getX() == null ? "-" : model.getCurrentDragon().getKiller().getLocation().getX().toString());
            killerYValue.setText(model.getCurrentDragon().getKiller().getLocation().getY() == -1 ? "-" : String.valueOf(model.getCurrentDragon().getKiller().getLocation().getY()));
            killerZValue.setText(model.getCurrentDragon().getKiller().getLocation().getZ() == null ? "-" : model.getCurrentDragon().getKiller().getLocation().getZ().toString());
        }
        else
            killerValue.setText("-");

        killerHeight.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!"-0123456789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        killerWeight.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!"-0123456789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        age.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!"-0123456789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        x.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!"-0123456789".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        y.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!"-0123456789.".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        killerX.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!"-0123456789.".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        killerY.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!"-0123456789.".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        killerZ.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!"-0123456789.".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });
        killerBirthday.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            //2024-02-07T16:15:24+00:00
            if (!"-0123456789TZ:+.".contains(keyEvent.getCharacter())) {
                keyEvent.consume();
            }
        });

        authorValue.setText(model.getCurrentDragon().getOwnerUsername());
    }

    @FXML
    private void update(Event event) {
        if (!updateOpened) {
            updateVBox.setVisible(true);
            valuesVBox.setVisible(false);
            name.setText(model.getCurrentDragon().getName());
            x.setText(String.valueOf(model.getCurrentDragon().getCoordinates().getX()));
            y.setText(String.valueOf(model.getCurrentDragon().getCoordinates().getY()));
            age.setText(String.valueOf(model.getCurrentDragon().getAge()));
            speaking.setSelected(model.getCurrentDragon().getSpeaking());
            color.setValue(model.getCurrentDragon().getColor());
            character.setValue(model.getCurrentDragon().getCharacter());
            killer.setSelected(model.getCurrentDragon().getKiller() != null);
            if (model.getCurrentDragon().getKiller() != null) {
                killerName.setText(model.getCurrentDragon().getKiller().getName() == null ? "" : model.getCurrentDragon().getKiller().getName());
                killerBirthday.setText(model.getCurrentDragon().getKiller().getBirthday() == null ? "" : model.getCurrentDragon().getKiller().getBirthday().toString());
                killerHeight.setText(model.getCurrentDragon().getKiller().getHeight() == null ? "" : model.getCurrentDragon().getKiller().getHeight().toString());
                killerWeight.setText(model.getCurrentDragon().getKiller().getWeight() == null ? "" : model.getCurrentDragon().getKiller().getWeight().toString());
                killerX.setText(model.getCurrentDragon().getKiller().getLocation().getX() == null ? "" : model.getCurrentDragon().getKiller().getLocation().getX().toString());
                killerY.setText(model.getCurrentDragon().getKiller().getLocation().getY() == -1 ? "" : String.valueOf(model.getCurrentDragon().getKiller().getLocation().getY()));
                killerZ.setText(model.getCurrentDragon().getKiller().getLocation().getZ() == null ? "" : model.getCurrentDragon().getKiller().getLocation().getZ().toString());
            }
            updateOpened = true;
        } else {
            try {
                model.updateDragon();
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                getParentModel().updateCollection();
            } catch (RuntimeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                System.out.println(e);
                alert.setHeaderText(getParentModel().getController().getResourceBundle().getString("date_parse_error"));
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            } catch (ExecutionControl.UserException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void remove(Event event) {
        getParentModel().executeCommand(new RemoveById(), Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession(), ArgumentType.ID, model.getCurrentDragon().getId()));
        getParentModel().updateCollection();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void close(Event event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private void bindProperties() {
        killer.setSelected(true);
        killer.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (killerName.isVisible()) {
                killerName.setVisible(false);
                killerBirthday.setVisible(false);
                killerHeight.setVisible(false);
                killerWeight.setVisible(false);
                killerX.setVisible(false);
                killerY.setVisible(false);
                killerZ.setVisible(false);
            } else {
                killerName.setVisible(true);
                killerBirthday.setVisible(true);
                killerHeight.setVisible(true);
                killerWeight.setVisible(true);
                killerX.setVisible(true);
                killerY.setVisible(true);
                killerZ.setVisible(true);
            }
        });
    }

    public TextField getName() {
        return name;
    }

    public CheckBox getKiller() {
        return killer;
    }

    public TextField getX() {
        return x;
    }

    public TextField getY() {
        return y;
    }

    public TextField getAge() {
        return age;
    }

    public CheckBox getSpeaking() {
        return speaking;
    }

    public ChoiceBox<Color> getColor() {
        return color;
    }

    public ChoiceBox<DragonCharacter> getCharacter() {
        return character;
    }

    public TextField getKillerName() {
        return killerName;
    }

    public TextField getKillerWeight() {
        return killerWeight;
    }

    public TextField getKillerHeight() {
        return killerHeight;
    }

    public TextField getKillerY() {
        return killerY;
    }

    public TextField getKillerZ() {
        return killerZ;
    }

    public TextField getKillerX() {
        return killerX;
    }

    public TextField getKillerBirthday() {
        return killerBirthday;
    }
}
