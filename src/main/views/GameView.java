package main.views;

import java.beans.PropertyChangeEvent;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import main.controllers.GameController;
import main.events.PlayerShootEvent;
import main.exceptions.SettingsNotLoaded;
import main.models.Game;
import main.models.components.entities.CommonShip;
import main.models.components.interfaces.Entity;
import main.utils.GameLoop;
import main.views.fire.Primary;
import main.views.entities.CommonShipView;
import main.views.entities.interfaces.EntitySprite;
import org.greenrobot.eventbus.EventBus;

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
