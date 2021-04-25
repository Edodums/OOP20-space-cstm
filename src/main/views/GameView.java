package main.views;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.controllers.GameController;
import main.controllers.SettingsController;
import main.events.EventManager;
import main.events.PlayerGoLeftEvent;
import main.events.PlayerGoRightEvent;
import main.events.PlayerShootEvent;
import main.exceptions.SettingsNotLoaded;
import main.models.Game;
import main.models.components.interfaces.Entity;
import main.utils.GameLoop;
import main.utils.Pair;
import main.views.entities.CommonShipView;
import main.views.entities.MotherShipView;
import main.views.entities.PlayerShipView;
import main.views.entities.interfaces.EntitySprite;
import main.views.fire.Primary;
import org.greenrobot.eventbus.EventBus;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.*;

public class GameView implements View, Initializable, MovementHandler {
  private static final GameController controller = new GameController(new Game(SettingsController.load()));
  private static final Map<Entity, EntitySprite> entitiesSprites = new HashMap<>();
  private static final double BOUND_FACTOR = 1.6;
  private static final double UNIT = 50;
  
  private Stage stage;
  private EventManager eventManager = new EventManager(controller.getModel());
  
  @FXML
  private AnchorPane parent;
  
  public GameView() {
    try {
      controller.getModel().addPropertyChangeListener(this);
      
      new LoginDialog(controller);
    } catch (SettingsNotLoaded e) {
      e.printStackTrace();
    }
  
    
    updateGrid();
  }
  
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()) {
      case "grid": updateGridView((Map<Pair<Double, Double>, Optional<Entity>>) evt.getNewValue());
    }
   // System.out.println("evt.getPropertyName()" +  evt.getPropertyName() + "evt.getNewValue()" +  evt.getNewValue() );
  }
  
  private void updateGridView(Map<Pair<Double, Double>, Optional<Entity>> grid) {
    grid
        .entrySet()
        .stream()
        .filter(entry -> entry.getValue().isPresent())
        .forEachOrdered((entry) -> {
                final Entity entity = entry.getValue().get();
                final Pair<Double, Double> position = entry.getKey();
                final EntitySprite entitySprite = getStrategy(entity);
              
                entitySprite.update(position, UNIT);
              }
        );
  }
  
  @Override
  public AnchorPane getParent() {
    return this.parent;
  }
  
  @Override
  public double getBoundFactor() {
    return BOUND_FACTOR;
  }
  
  public Stage getStage() {
    return this.stage;
  }
  
  public void setStage(Stage stage)  {
    this.stage = stage;
  }
  
  private EntitySprite getStrategy(Entity entity) {
    if (!entitiesSprites.containsKey(entity)) {
      EntitySprite entitySprite;
      
      switch (entity.getClass().getSimpleName()) {
        case "CommonShip":
          entitySprite = new CommonShipView();
          break;
        case "MotherShip":
          entitySprite = new MotherShipView();
          break;
        case "PlayerShip":
          entitySprite = new PlayerShipView();
          break;
        default:
          throw new IllegalStateException("Unexpected Strategy value: " + entity.getClass().getName());
      }
      
      entitiesSprites.put(entity, entitySprite);
      
      return entitySprite;
    }
    
    return entitiesSprites.get(entity);
  }
  
  private void primaryFireEvent() {
    getParent().setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode().equals(KeyCode.SPACE)) {
        new Primary(controller.getModel());
        
        EventBus.getDefault().post(new PlayerShootEvent());
      }
    });
  }
  
  @Override
  public void movementHandler() {
    getStage().getScene().setOnKeyPressed(keyEvent -> {
      final PlayerShipView view = (PlayerShipView) entitiesSprites
                                                 .values()
                                                 .stream()
                                                 .filter(entity -> entity.getClass().equals(PlayerShipView.class))
                                                 .findFirst()
                                                 .get();
      
      if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
        EventBus.getDefault().post(new PlayerGoRightEvent(view, UNIT));
      }
      
      if (keyEvent.getCode().equals(KeyCode.LEFT)) {
        EventBus.getDefault().post(new PlayerGoLeftEvent(view, UNIT));
      }
      
    });
  }
  
  private void updateGrid() {
    GameLoop timer = new GameLoop() {
      @Override
      public void tick(float secondsSinceLastFrame) {
        try {
          Thread.sleep(300);
          controller.updateGrid();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    
    timer.start();
  }
  
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // make setStage in interface so it can be set from the switch in MEnuview
    
    controller
          .getModel()
          .getGrid()
          .entrySet()
          .stream()
          .filter(entry -> entry.getValue().isPresent())
          .forEachOrdered((entry) -> {
            final Entity entity = entry.getValue().get();
            final Pair<Double, Double> position = entry.getKey();
            final EntitySprite entitySprite = getStrategy(entity);
            
            entitySprite.create(position, entity, UNIT);
            entitySprite.add(getParent());
          }
    );
    
    getParent().requestFocus();
    primaryFireEvent();
  
    /*for (int i = 0; i < 5; i++) {
      controller.updateGrid();
    }*/
  }
}
