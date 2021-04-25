package main.views.fire;

import main.models.Game;

// TODO: check if is better as an abstract class or as an interface
public abstract class FireCommand {
    private Game game;

    FireCommand(Game game) {
        this.game = game;
    }

    public abstract void execute();
    
    public Game getGame() {
        return this.game;
    }
}
