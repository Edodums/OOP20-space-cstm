package main.views;

import com.sun.jdi.ClassNotLoadedException;
import main.controllers.GameController;
import main.models.Game;

import java.beans.PropertyChangeEvent;

public class GameView extends View {
    private Game game;
    public GameView(GameController controller) {
        try {
            game = new Game();
            game.addPropertyChangeListener(this);

            new LoginDialog(game);
        } catch (ClassNotLoadedException e) {
            e.printStackTrace();
        }
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
