package main.views.command;

import main.models.Game;

// TODO: check if is better as an abstract class or as an interface
public abstract class Command {
    public Game game;

    Command(Game game) {
        this.game = game;
    }

    public abstract void execute();
}
