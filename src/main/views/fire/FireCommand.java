package main.views.fire;

import main.models.Game;

// TODO: check if is better as an abstract class or as an interface
public abstract class FireCommand {
    public Game game;

    FireCommand(Game game) {
        this.game = game;
    }

    public abstract void execute();
}
