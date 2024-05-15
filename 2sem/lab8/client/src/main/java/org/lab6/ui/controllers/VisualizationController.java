package org.lab6.ui.controllers;

import common.models.Dragon;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.lab6.ui.ChildUIType;
import org.lab6.ui.GUIConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VisualizationController extends DataVisualizerController {

    @FXML
    private AnchorPane visualizationPane;
    private Dragon chosenDragon;
    private final Map<Dragon, Canvas> dragons = new HashMap<>();
    private static final Map<String, Color> colors = new HashMap<>();

    private Canvas generateCanvas(Dragon dragon) {
        checkAuthorColor(dragon);
        Canvas dragonCanvas = new Canvas(50, 50);
        dragonCanvas.setOnMouseEntered(event -> {
            dragonCanvas.setScaleX(1.13);
            dragonCanvas.setScaleY(1.13);
        });
        dragonCanvas.setOnMouseExited(event -> {
            dragonCanvas.setScaleX(1);
            dragonCanvas.setScaleY(1);
        });
        dragonCanvas.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
                chosenDragon = dragon;
                getParentController().loadUI(GUIConfig.PROFILE_WINDOW_PATH, null, ChildUIType.DRAGON_PROFILE_CHILD_WINDOW);
            }
        });
        double layoutX = 525 + dragon.getCoordinates().getX()*1.05;
        double layoutY = 225 - (dragon.getCoordinates().getY() * 0.9);

        layoutX = Math.max(0, layoutX);
        layoutX = Math.min(1000, layoutX);

        layoutY = Math.max(50, layoutY);
        layoutY = Math.min(450, layoutY);

        dragonCanvas.setLayoutX(layoutX);
        dragonCanvas.setLayoutY(layoutY);
        if (dragon.getKiller() == null) {
            drawDragonWithoutKiller(dragon, dragonCanvas);
        } else {
            drawDragonWithKiller(dragon, dragonCanvas);
        }
        return dragonCanvas;
    }

    private void drawDragonWithoutKiller(Dragon dragon, Canvas dragonCanvas) {
        checkAuthorColor(dragon);
        GraphicsContext gc = dragonCanvas.getGraphicsContext2D();
        gc.setFill(colors.get(dragon.getOwnerUsername()));

        // Body
        gc.fillRect(17, 3, 15, 8);
        gc.fillRect(18, 2, 13, 8);
        gc.fillRect(17, 6, 6, 8);
        gc.fillRect(17, 13, 11, 2);
        gc.fillRect(17, 18, 8, 2);
        gc.fillRect(24, 18, 1, 3);
        gc.fillRect(16, 15, 5, 10);
        gc.fillRect(14, 17, 6, 10);
        gc.fillRect(12, 18, 5, 10);
        gc.fillRect(12, 20, 3, 10);
        gc.fillRect(16, 20, 2, 10);
        gc.fillRect(17, 20, 1, 15);
        gc.fillRect(17, 33, 3, 1);
        gc.fillRect(12, 20, 1, 12);
        gc.fillRect(12, 30, 3, 1);
        gc.fillRect(10, 20, 3, 9);
        gc.fillRect(8, 20, 2, 7);
        gc.fillRect(7, 18, 2, 7);
        gc.fillRect(6, 17, 2, 7);
        gc.fillRect(5, 15, 1, 7);

        // Eye
        gc.setFill(Color.WHITE);
        gc.fillRect(20, 4, 2, 2);
    }
    private void drawDragonWithKiller(Dragon dragon, Canvas dragonCanvas) {
        checkAuthorColor(dragon);
        GraphicsContext gc = dragonCanvas.getGraphicsContext2D();
        gc.setFill(colors.get(dragon.getOwnerUsername()));

        // Body
        gc.fillRect(17, 3, 15, 8);
        gc.fillRect(18, 2, 13, 8);
        gc.fillRect(17, 6, 6, 8);
        gc.fillRect(17, 13, 11, 2);
        gc.fillRect(17, 18, 8, 2);
        gc.fillRect(24, 18, 1, 3);
        gc.fillRect(16, 15, 5, 10);
        gc.fillRect(14, 17, 6, 10);
        gc.fillRect(12, 18, 5, 10);
        gc.fillRect(12, 20, 3, 10);
        gc.fillRect(16, 20, 2, 10);
        gc.fillRect(17, 20, 1, 15);
        gc.fillRect(17, 33, 3, 1);
        gc.fillRect(12, 20, 1, 12);
        gc.fillRect(12, 30, 3, 1);
        gc.fillRect(10, 20, 3, 9);
        gc.fillRect(8, 20, 2, 7);
        gc.fillRect(7, 18, 2, 7);
        gc.fillRect(6, 17, 2, 7);
        gc.fillRect(5, 15, 1, 7);

        // Eye
        gc.setFill(Color.WHITE);
        gc.fillRect(20, 4, 2, 2);

        gc.setLineWidth(2);
        gc.setFill(colors.get(dragon.getOwnerUsername()));
        gc.setStroke(colors.get(dragon.getOwnerUsername()));
        gc.strokeLine(35, 20, 35, 30);
        //legs
        gc.strokeLine(30, 33, 35, 30);
        gc.strokeLine(40, 33, 35, 30);

        //hands
        gc.strokeLine(30, 18, 35, 23);
        gc.strokeLine(40, 18, 35, 23);

        //head
        gc.strokeOval(32, 12, 6, 6);
    }

    private void checkAuthorColor(Dragon dragonBeing) {
        if (!colors.containsKey(dragonBeing.getOwnerUsername())) {
            colors.put(dragonBeing.getOwnerUsername(), Color.color(Math.random(), Math.random(), Math.random()));
        }
    }

    @Override
    public synchronized void updateInfo(List<Dragon> elementsToRemove, List<Dragon> elementsToAdd, List<Dragon> elementsToUpdate) {
        elementsToRemove.forEach(this::removeFromVisualization);
        elementsToAdd.forEach(this::addToVisualization);
        List<Integer> idsToUpdate = elementsToUpdate.stream().map(Dragon::getId).toList();
        for (Integer id : idsToUpdate) {
            for (Dragon dragon : dragons.keySet()) {
                if (dragon.getId() == id) {
                    removeFromVisualization(dragon);
                }
            }
        }
        for (Dragon dragon : elementsToUpdate) {
            addToVisualization(dragon);
        }
    }

    @Override
    public void setInfo(List<Dragon> elementsToSet) {
        elementsToSet.forEach(this::addToVisualization);
    }

    private void addToVisualization(Dragon dragon) {
        Canvas canvas = generateCanvas(dragon);
        ScaleTransition transition = new ScaleTransition();
        transition.setNode(canvas);
        transition.setDuration(Duration.millis(1000));
        transition.setFromX(0);
        transition.setFromY(0);
        transition.setToX(1);
        transition.setToY(1);
        dragons.put(dragon, canvas);
        transition.play();
        visualizationPane.getChildren().add(canvas);
    }

    private void removeFromVisualization(Dragon dragon) {
        visualizationPane.getChildren().remove(dragons.get(dragon));
        dragons.remove(dragon);
    }

    @Override
    public Dragon getChosenDragon() {
        return chosenDragon;
    }

    @Override
    public void setChosenDragon(Dragon dragon) {
        this.chosenDragon = dragon;
    }
}