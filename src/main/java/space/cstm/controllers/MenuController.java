package space.cstm.controllers;

import space.cstm.models.Menu;
import space.cstm.models.ObservableModel;
import space.cstm.utils.enums.CurrentScene;

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

