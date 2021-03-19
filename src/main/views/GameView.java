package main.views;

import main.controllers.GameController;
import main.models.Game;

import java.beans.PropertyChangeEvent;

public class GameView extends View {
    public GameView(GameController controller) {
        Game game = new Game();
        LoginDialog loginDialog = new LoginDialog(game);

        game.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();

        /*
            Useful link:
            https://docs.oracle.com/javase/tutorial/uiswing/events/propertychangelistener.html

            if (source == amountField) {
            amount = ((Number)amountField.getValue()).doubleValue();
            ...
        }*/
    }
}
