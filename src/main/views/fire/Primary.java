package main.views.fire;

import main.models.Game;

public class Primary extends FireCommand {
    public Primary(Game game) {
        super(game);
    }

    @Override
    public void execute() {
        game.primaryFire();
    }
}
