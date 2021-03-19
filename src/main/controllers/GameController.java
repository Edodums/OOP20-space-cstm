package main.controllers;

import main.models.Game;
import main.models.ObservableModel;
import main.models.Settings;
import main.views.GameView;
import main.views.View;

public class GameController extends Controller {
    private Game model;
    private View view;

    GameController(Settings settings) {
        //TODO: check if still MVC like this, probably not
        setModel(new Game());
        setView(new GameView(this));
        initEntities(settings);
    }

    private void initEntities(Settings settings) {
        this.model.initEntities(settings);
    }

    @Override
    protected void setModel(ObservableModel model) {
        this.model = (Game) model;
    }

    @Override
    protected void setView(View view) {
        this.view = view;
    }
}
