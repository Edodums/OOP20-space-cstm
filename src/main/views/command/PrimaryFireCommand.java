package main.views.command;

import main.models.Game;

public class PrimaryFireCommand extends Command {
    public PrimaryFireCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.primaryFire();
    }
}
