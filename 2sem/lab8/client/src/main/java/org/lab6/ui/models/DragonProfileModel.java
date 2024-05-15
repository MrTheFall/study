package org.lab6.ui.models;

import common.models.Coordinates;
import common.models.Dragon;
import common.models.Location;
import common.models.Person;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import javafx.scene.Scene;
import jdk.jshell.spi.ExecutionControl;
import org.lab6.auth.SessionHandler;
import org.lab6.commands.Update;
import org.lab6.ui.controllers.DragonProfileController;
import org.lab6.ui.controllers.TableViewController;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

public class DragonProfileModel  {
    private final DragonProfileController controller;
    private final Dragon currentDragon;

    public DragonProfileModel(DragonProfileController controller, Dragon dragon) {
        this.controller = controller;
        this.currentDragon = dragon;
    }

    public void updateDragon() throws ExecutionControl.UserException {
        Dragon newDragon = new Dragon();
        try {

            newDragon.setName(controller.getName().getText());
            newDragon.setOwnerUsername(SessionHandler.currentUser.getUsername());
            newDragon.setCoordinates(new Coordinates(currentDragon.getCoordinates().getId(), Double.parseDouble(controller.getX().getText()), Long.parseLong(controller.getY().getText())));
            newDragon.setAge(Integer.parseInt(controller.getAge().getText()));
            newDragon.setCreationDate(LocalDateTime.now());
            newDragon.setSpeaking(controller.getSpeaking().isSelected());
            newDragon.setColor(controller.getColor().getValue());
            newDragon.setCharacter(controller.getCharacter().getValue());
            if (controller.getKiller().isSelected()) {
                System.out.println("CREATING KILLER");
                newDragon.setKiller(new Person(currentDragon.getKiller() != null ? currentDragon.getKiller().getId() : -1, controller.getKillerName().getText(), controller.getKillerBirthday().getText().isEmpty() ? null : ZonedDateTime.parse(controller.getKillerBirthday().getText()), controller.getKillerHeight().getText().isEmpty() ? null : Float.parseFloat(controller.getKillerHeight().getText()), controller.getKillerWeight().getText().isEmpty() ? null : Long.parseLong(controller.getKillerWeight().getText()),
                        new Location(currentDragon.getKiller().getLocation() != null ? currentDragon.getKiller().getLocation().getId() : -1, Float.parseFloat(controller.getKillerX().getText()), Float.parseFloat(controller.getKillerY().getText()), Long.parseLong(controller.getKillerZ().getText()))));
            }
//            else if (!controller.getCarCheckBox().isSelected() && currentDragon.getCar() != null) {
//                newDragon.setCar(null);
//            } else {
//                if (!controller.getCarSpeedField().getText().equals("")) {
//                    newDragon.getCar().setCarSpeed(Integer.parseInt(controller.getCarSpeedField().getText()));
//                }
//                newDragon.getCar().setCarCoolness(controller.getCarCoolnessCheckBox().isSelected());
//            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Произошла ошибка при обновлении дракона " + e);
        }
        boolean isValid = newDragon.validate();
        if (!isValid) {
            throw new RuntimeException("Поля дракона невалидны");
        }
        //Response response = controller.getParentModel().getConnectionHandler().sendAndReceiveCommand(new Request(new Update(), Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession(), ArgumentType.DRAGON, newDragon)));
        controller.getParentModel().executeCommand(new Update(), Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession(), ArgumentType.ID, currentDragon.getId(), ArgumentType.DRAGON, newDragon));
    }

    public Dragon getCurrentDragon() {
        return currentDragon;
    }

}
