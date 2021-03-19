package main.controllers;

import main.models.MenuModel;
import main.utils.CurrentScene;

public class MenuController extends Controller {

    private MenuModel model;


    public MenuController(MenuModel model) {
        this.model = model ;
    }

    public void setCurrentScene (CurrentScene currentScene) {
        this.model.setCurrentScene(currentScene);
    }

}
