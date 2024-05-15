package org.lab6.ui.controllers;

import common.models.Color;
import common.models.Dragon;
import common.models.DragonCharacter;
import common.utils.ArgumentType;
import common.utils.Command;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl;
import org.lab6.auth.SessionHandler;
import org.lab6.commands.Add;
import org.lab6.commands.AddIfMax;
import org.lab6.ui.models.AddCommandModel;

import java.util.Map;

public class AddCommandController extends ChildWindowController {

    @FXML
    private TextField name, x, y, age, killerName, killerBirthday, killerWeight, killerHeight, killerX, killerY, killerZ;
    @FXML
    private CheckBox speaking, killerCheckBox, addIfMaxCheckBox;
    @FXML
    private ChoiceBox color, character;
    @FXML
    private Button applyButton, clearFieldsButton;
    @FXML
    private CheckBox addIfMinCheckBox;

    private final ObservableList<Color> colors = FXCollections.observableArrayList(Color.values());
    private final ObservableList<DragonCharacter> characters = FXCollections.observableArrayList(DragonCharacter.values());
    private final AddCommandModel model = new AddCommandModel(this);


    @FXML
    private void initialize() {
        bindProperties();
        color.setItems(colors);
        character.setItems(characters);
    }

    protected void bindProperties() {
        BooleanBinding booleanBind = name.textProperty().isEmpty()
                .or(x.textProperty().isEmpty())
                .or(y.textProperty().isEmpty()
                        .or(name.textProperty().isEmpty())
                        .or(age.textProperty().isEmpty())
                        .or(color.valueProperty().isNull())
                .or(killerCheckBox.selectedProperty().and(killerName.textProperty().isEmpty().or(killerX.textProperty().isEmpty().or(killerY.textProperty().isEmpty().or(killerZ.textProperty().isEmpty()))))));

        applyButton.disableProperty().bind(booleanBind);

        killerCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (killerName.isVisible()) {
                killerName.setVisible(false);
                killerBirthday.setVisible(false);
                killerWeight.setVisible(false);
                killerHeight.setVisible(false);
                killerX.setVisible(false);
                killerY.setVisible(false);
                killerZ.setVisible(false);
            } else {
                killerName.setVisible(true);
                killerBirthday.setVisible(true);
                killerWeight.setVisible(true);
                killerHeight.setVisible(true);
                killerX.setVisible(true);
                killerY.setVisible(true);
                killerZ.setVisible(true);
            }
        });

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
    }

    @FXML
    private void clear() {
        name.setText("");
        x.setText("");
        y.setText("");
        age.setText("");
        speaking.setSelected(false);
        killerName.setText("");
        killerBirthday.setText("");
        killerWeight.setText("");
        killerHeight.setText("");
        killerX.setText("");
        killerY.setText("");
        killerZ.setText("");
        killerCheckBox.setSelected(true);
        character.setValue(null);
        color.setValue(null);
    }

    public TextField getName() {
        return name;
    }

    public CheckBox getKillerCheckBox() {
        return killerCheckBox;
    }

    public TextField getKillerHeight() {
        return killerHeight;
    }

    public TextField getKillerWeight() {
        return killerWeight;
    }

    public void setKillerName(TextField killerName) {
        this.killerName = killerName;
    }

    public TextField getX() {
        return x;
    }

    public TextField getY() {
        return y;
    }

    public CheckBox getSpeaking() {
        return speaking;
    }

    public ChoiceBox getCharacter() {
        return character;
    }

    public ChoiceBox getColor() {
        return color;
    }

    public TextField getAge() {
        return age;
    }

    public TextField getKillerBirthday() {
        return killerBirthday;
    }

    public TextField getKillerName() {
        return killerName;
    }

    public TextField getKillerX() {
        return killerX;
    }

    public TextField getKillerY() {
        return killerY;
    }

    public TextField getKillerZ() {
        return killerZ;
    }




    @FXML
    private void apply(Event event) {
        try {
            Dragon createdDragon = model.createDragon();
            Command command;
            if (addIfMaxCheckBox.isSelected()) {
                command = new AddIfMax();
            } else {
                command = new Add();
            }
            super.getParentModel().executeCommand(command, Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession(), ArgumentType.DRAGON, createdDragon));
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            super.getParentModel().updateCollection();
        } catch (RuntimeException e) {
            System.err.println(e);
        } catch (ExecutionControl.UserException e) {
            throw new RuntimeException(e);
        }
    }
}
