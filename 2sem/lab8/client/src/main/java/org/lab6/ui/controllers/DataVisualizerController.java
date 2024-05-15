package org.lab6.ui.controllers;

import common.models.Dragon;

import java.util.List;

public abstract class DataVisualizerController {

    private MenuFormController parentController;

    public abstract void updateInfo(List<Dragon> elementsToRemove, List<Dragon> elementsToAdd, List<Dragon> elementsToUpdate);

    public abstract void setInfo(List<Dragon> elementsToSet);

    public MenuFormController getParentController() {
        return parentController;
    }

    public void setParentController(MenuFormController parentController) {
        this.parentController = parentController;
    }

    public abstract Dragon getChosenDragon();

    public abstract void setChosenDragon(Dragon dragon);
}