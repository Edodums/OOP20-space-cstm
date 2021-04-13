package main.views;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import main.controllers.GameController;
import main.events.PlayerShootEvent;
import main.exceptions.SettingsNotLoaded;
import main.models.Game;
import main.models.components.MotherShip;
import main.models.components.entities.CommonShip;
import main.models.components.interfaces.Entity;
import main.utils.GameLoop;
import main.views.entities.CommonShipView;
import main.views.entities.MotherShipView;
import main.views.entities.interfaces.EntitySprite;
import main.views.fire.Primary;
import org.greenrobot.eventbus.EventBus;

import java.beans.PropertyChangeEvent;
import java.util.Objects;

public class GameView implements View {
  private final static double BOUND_FACTOR = 1.8;
  private final static double UNIT = 50;
  
  private Game model;
  private GameController controller;
  private GridPane parent;

  public GameView(Game model, GameController controller) {
    try {
      this.model = model;
      this.model.addPropertyChangeListener(this);
      this.controller = controller;

      new LoginDialog(controller);
    } catch (SettingsNotLoaded e) {
      e.printStackTrace();
    }
    
    primaryFireEvent();
    init();
    updateCommonShip();
  }
  
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String propertyName = evt.getPropertyName();
    Object newValue = evt.getNewValue();
    // TODO: finish
  }
  
  private void init() {
    model.getEntitySet().forEach(entity -> {
      addToParent(Objects.requireNonNull(getStrategy(entity)).create(model.getFilteredEntitiesInGrid(entity), UNIT));
    });
  }
  
  private EntitySprite getStrategy(Entity entity) {
    if (entity instanceof CommonShip) {
      return new CommonShipView();
    }

    if (entity instanceof MotherShip){
      return new MotherShipView();
    }
    
    return null;
  }
  
  private void primaryFireEvent() {
    getParent().setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode().equals(KeyCode.SPACE)) {
        new Primary(model);
        
        EventBus.getDefault().post(new PlayerShootEvent());
      }
    });
  }

  private void movementHandler() {
    getParent().setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
        //   TODO: Player right
      }
      if (keyEvent.getCode().equals(KeyCode.LEFT)) {
        //   TODO: Player left
      }
    });
  }
  private void updateCommonShip() {
    GameLoop timer = new GameLoop() {
      @Override
      public void tick(float secondsSinceLastFrame) {
        controller.updateGrid();
      }
    };
    
    timer.start();
  }
  
  private void addToParent(Node... nodes) {
    getParent().getChildren().addAll(nodes);
  }
  
  @Override
  public GridPane getParent() {
    return this.parent;
  }
  
  @Override
  public double getBoundFactor() {
    return BOUND_FACTOR;
  }
}
