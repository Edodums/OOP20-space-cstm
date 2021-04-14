package main.controllers;

import main.models.Menu;
import main.models.ObservableModel;
import main.utils.CurrentScene;
import main.views.View;

public class MenuController extends Controller {

    private Menu model;
    private View view;


    public MenuController(Menu model) {
        this.model = model ;
    }

    public void setCurrentScene (CurrentScene currentScene) {
        this.model.setCurrentScene(currentScene);
    }

    @Override
    protected void setModel(ObservableModel model) { this.model = (Menu) model; }

    @Override
    protected void setView(View view) { this.view = view; }
}
