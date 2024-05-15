package org.lab6.ui.models;

import common.models.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jdk.jshell.spi.ExecutionControl;
import org.lab6.auth.SessionHandler;
import org.lab6.ui.controllers.AddCommandController;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class AddCommandModel {

    private final AddCommandController controller;

    public AddCommandModel(AddCommandController controller) {
        this.controller = controller;
    }

    public Dragon createDragon() throws ExecutionControl.UserException {
        try {
            Dragon newDragon = new Dragon();
            newDragon.setName(controller.getName().getText());
            newDragon.setOwnerUsername(SessionHandler.currentUser.getUsername());
            newDragon.setCoordinates(new Coordinates(-1, Long.parseLong(controller.getX().getText()), Long.parseLong(controller.getY().getText())));
            newDragon.setAge(Integer.parseInt(controller.getAge().getText()));
            newDragon.setCreationDate(LocalDateTime.now());
            newDragon.setSpeaking(controller.getSpeaking().isSelected());
            newDragon.setColor(Color.valueOf((controller.getColor().getValue().toString())));

            if (controller.getCharacter().getValue() != null)
                newDragon.setCharacter(DragonCharacter.valueOf((controller.getCharacter().getValue().toString())));

            if (controller.getKillerCheckBox().isSelected()) {
                newDragon.setKiller(new Person(-1, controller.getKillerName().getText(), controller.getKillerBirthday().getText().isEmpty() ? null : ZonedDateTime.parse(controller.getKillerBirthday().getText()),
                        controller.getKillerWeight().getText().isEmpty() ? null : Float.parseFloat(controller.getKillerWeight().getText()), controller.getKillerHeight().getText().isEmpty() ? null : Long.parseLong(controller.getKillerHeight().getText()),
                        new Location(-1, Float.parseFloat(controller.getKillerX().getText()), Float.parseFloat(controller.getKillerY().getText()), Long.parseLong(controller.getKillerZ().getText()))));
            }
            validateDragon(newDragon);
            return newDragon;
        } catch (NumberFormatException e) {
            throw new ExecutionControl.UserException(controller.getParentModel().getController().getResourceBundle().getString("empty_numeric_fields_error"), null, null);
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText(controller.getParentModel().getController().getResourceBundle().getString("date_parse_error"));
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        throw new ExecutionControl.UserException("error_while_adding_dragon", null, null);
    }

    private void validateDragon(Dragon dragon) throws ExecutionControl.UserException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String errorMessage = "";
        if (!(dragon.getName().length() > 1 && dragon.getName().length() < 100)) {
            errorMessage += controller.getParentModel().getController().getResourceBundle().getString("name_length_error") + '\n';
        }
        if (!(dragon.getAge() > 0)) {
            errorMessage += controller.getParentModel().getController().getResourceBundle().getString("age_bound_error") + '\n';
        }
        if (!errorMessage.equals("")) {
            alert.setTitle("Error");
            alert.setHeaderText(errorMessage);
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }
}
