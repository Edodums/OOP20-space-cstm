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
        setModel(new Game(settings));
        setView(new GameView(this));
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
