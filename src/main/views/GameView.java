package main.views;

import java.beans.PropertyChangeEvent;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import main.exceptions.SettingsNotLoaded;
import main.models.Game;
import main.models.components.entities.CommonShip;
import main.models.components.interfaces.Entity;
import main.utils.GameLoop;
import main.views.command.PrimaryFireCommand;
import main.views.entities.CommonShipView;
import main.views.entities.interfaces.EntitySprite;

public class GameView extends View {
  private Game game;
  private static EntitySprite strategy;

  public GameView(Game game) {
    try {
      this.game = game;
      
      game.addPropertyChangeListener(this);

      new LoginDialog(game);
    } catch (SettingsNotLoaded e) {
      e.printStackTrace();
    }
    
    primaryFireEvent();
    init();
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
  
  private void init() {
    game.getEntitySet().forEach(entity -> {
      strategy = getStrategy(entity);
      addToParent(Objects.requireNonNull(strategy)
                        .create(game.getFilteredEntitiesInGrid(entity)));
    });
  }
  
  private EntitySprite getStrategy(Entity entity) {
    if (entity.getClass().equals(CommonShip.class)) {
      return new CommonShipView();
    }
    
    return null;
  }
  
  private void primaryFireEvent() {
    getParent().setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode().equals(KeyCode.SPACE)) {
        new PrimaryFireCommand(game);
      }
    });
  }
  
  private void updateCommonShip() {
    GameLoop timer = new GameLoop() {
      @Override
      public void tick(float secondsSinceLastFrame) {
        game.updateGrid();
        
      }
    };
    
    timer.start();
    // TODO: Event Manager could be useful here
  }
  
  private void addToParent(Node... nodes) {
    getParent().getChildren().addAll(nodes);
  }
}
