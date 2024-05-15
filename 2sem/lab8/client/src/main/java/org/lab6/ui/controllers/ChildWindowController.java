package org.lab6.ui.controllers;

import org.lab6.ui.models.MenuModel;

public abstract class ChildWindowController {

    private MenuModel parentModel;

    public void setMenuModel(MenuModel model) {
        this.parentModel = model;
    }

    public MenuModel getParentModel() {
        return parentModel;
    }

}