package main.controllers;

import main.models.Menu;
import main.models.ObservableModel;
import main.utils.enums.CurrentScene;

public class MenuController implements Controller {
    private Menu model;

    public MenuController(Menu model) {
        this.model = model;
    }

    @Override
    public void setModel(ObservableModel model) { this.model = (Menu) model; }

    public void setCurrentScene (CurrentScene currentScene) {
        this.model.setCurrentScene(currentScene);
    }
}

