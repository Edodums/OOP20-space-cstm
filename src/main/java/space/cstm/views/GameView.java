package space.cstm.views;



import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import space.cstm.controllers.GameController;
import space.cstm.controllers.RankingController;
import space.cstm.controllers.SettingsController;
import space.cstm.events.EventManager;
import space.cstm.exceptions.SettingsNotLoaded;
import space.cstm.models.Game;
import space.cstm.models.components.Collider;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.models.components.interfaces.Weapon;
import space.cstm.utils.GameLoop;
import space.cstm.utils.Pair;
import space.cstm.utils.enums.CurrentScene;
import space.cstm.views.entities.CommonShipView;
import space.cstm.views.entities.MotherShipView;
import space.cstm.views.entities.PlayerShipView;
import space.cstm.views.fire.EnemyFireView;
import space.cstm.views.sprite.EntitySprite;
import space.cstm.views.fire.PrimaryFireView;
import space.cstm.views.sprite.WeaponSprite;

/**
 * UI Logic of the game stands here
 */
public class GameView implements View, Initializable, KeyEventListener {
  private final GameController controller = new GameController(new Game(SettingsController.load()) ); // loads the controller and pass the loaded settings
  private static final Map<Entity, EntitySprite> entitiesSprites = new HashMap<>(); // Association between an Entity class and EntitySprite one
  private static final Map<Weapon, WeaponSprite> beamsSprites = new HashMap<>(); // Association between an Weapon class and WeaponSprite one
  
  private GameLoop timer;
  private Task<Void> task;
  private Thread thread;
  private Stage stage;
  private EventHandler<KeyEvent> keyHandler;
  private float unit;
  
  private final EventManager eventManager = new EventManager(controller.getModel(), this);
 
  @FXML
  private AnchorPane parent;

  public GameView() {
    try {
      addListenerToModel(controller.getModel());
      controller.initGrid();
      controller.setAliveEnemies();
      
      new LoginDialog(controller);
    } catch (SettingsNotLoaded e) {
      e.printStackTrace();
    }
  
    setTask();
    update();
    startThread();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()) {
      case "weapon":
        updateLasers((Weapon) evt.getNewValue());
        break;
      case "removeWeapon":
        removeBeam((Weapon) evt.getNewValue());
        break;
      case "grid":
        updateGridView((Map<Pair<Float, Float>, Optional<Entity>>) evt.getNewValue());
        break;
      case "removeFromGrid":
        removeFromGridView((Entity) evt.getNewValue());
        break;
    }
  }
  
  private void removeFromGridView(Entity entityToDelete) {
   Map<Entity, EntitySprite> spriteMap = new HashMap<>(entitiesSprites);
  
    spriteMap.forEach((entity, sprite) -> {
      if (entityToDelete.equals(entity)) {
        entitiesSprites.remove(entity, sprite);
        sprite.remove(getParent());
      }
    });
  }
  
  private void updateLasers(Weapon weapon) {
    final Pair<Float, Float> position = ((Collider) weapon).getPosition();
    final WeaponSprite beamSprite = beamsSprites.get(weapon);
    
    if (position != null && beamSprite != null) {
      final float positionY = position.getY();
      final ImageView beam = beamSprite.get();
      
      beam.translateYProperty().set(positionY * unit);
    }
  }
  
  private void updateGridView(Map<Pair<Float, Float>, Optional<Entity>> grid) {
    grid
        .entrySet()
        .stream()
        .filter(entry -> entry.getValue().isPresent())
        .forEachOrdered((entry) -> {
                final Entity entity = entry.getValue().get();
                final Pair<Float, Float> position = entry.getKey();
                final EntitySprite entitySprite = getStrategy(entity);
                
                entitySprite.update(position, this.unit);
              }
        );
  }
  
  private void update() {
    this.timer = new GameLoop() {
      @Override
      public void tick(float secondsSinceLastFrame) {
        controller.updateGrid();
        controller.moveLasers();
        
        final Map<Weapon, WeaponSprite> beamsSpritesCopy = new HashMap<>(beamsSprites);
        
        beamsSpritesCopy.forEach((weapon, weaponSprite) -> {
          final float beamYPosition = (float) weaponSprite.get().getBoundsInParent().getMinY();
          final float beamHeight = (float) weaponSprite.get().getFitHeight();
          
          if (beamYPosition <= beamHeight || controller.collisionHandler(weapon) || beamYPosition > getHeight()) {
            controller.removeLaserInstance(weapon);
          }
        });
      }
    };
  }
  

  @Override
  public void addKeyEventHandler() {
    this.keyHandler = keyEvent -> {

      if (keyEvent.getCode().equals(KeyCode.SPACE)) {
        primaryFireHandler();
      } else {
        movementHandler(keyEvent.getCode());
      }
    };
  }
  
  @Override
  public void keyListener() {
    getStage().getScene().setOnKeyPressed(this.keyHandler);
  }
  
  public void primaryFireHandler() {
    final Weapon primaryWeapon = controller.primaryFire();
    
    if (primaryWeapon != null) {
      final PrimaryFireView primaryView = new PrimaryFireView();
  
      primaryView.create(((Collider) primaryWeapon).getPosition(), primaryWeapon, this.unit);
  
      beamsSprites.put(primaryWeapon, primaryView);
  
      primaryView.add(getParent());
    }
  }
  
  public void movementHandler(KeyCode code) {
    final ImageView view = getPlayerNode();
    final Pair<Float, Float> playerPosition = controller.getPlayerPosition();
    final float windowWidth = (float) getStage().getScene().getWindow().widthProperty().get();

    if (view != null) {
      final boolean hasReachedMinX = view.getLayoutX() - this.unit < 0;
      final boolean hasReachedMaxX = view.getLayoutX() + this.unit >= windowWidth - view.getFitWidth();

      if (code.equals(KeyCode.RIGHT) && !hasReachedMaxX) {
        playerPosition.setX(playerPosition.getX() + 1);
      }

      if (code.equals(KeyCode.LEFT) && !hasReachedMinX) {
        playerPosition.setX(playerPosition.getX() - 1);
      }

      view.setLayoutX(playerPosition.getX() * this.unit);
    }
  }
  
  private void removeBeam(Weapon weapon) {
    final Map<Weapon, WeaponSprite> beamsSpriteTemp = new HashMap<>(beamsSprites);
    final WeaponSprite weaponSprite = beamsSpriteTemp.get(weapon);
    
    if (weaponSprite != null) {
      weaponSprite.remove(getParent());
      beamsSprites.remove(weapon, weaponSprite);
    }
  }
  
  private static float gcd(float a, float b) {
    if (b == 0) {
      return a;
    }
    
    return gcd(b, a%b);
  }
  
  /* https://www.geeksforgeeks.org/minimum-squares-to-evenly-cut-a-rectangle/ */
  private float getUnit() {
    return gcd(getHeight(), getWidth());
  }
  
  private ImageView getPlayerNode() {
    return (ImageView) getParent().lookup("#player");
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
  
  @Override
  public AnchorPane getParent() {
    return this.parent;
  }
  
  public Stage getStage() {
    return this.stage;
  }
  
  public void setStage(Stage stage)  {
    this.stage = stage;
  }

  public void enemyFireHandler() {
    final Weapon enemyWeapon = controller.enemyFire();

    if (enemyWeapon != null) {
      final EnemyFireView enemyFireView = new EnemyFireView();

      enemyFireView.create(((Collider) enemyWeapon).getPosition(), enemyWeapon, this.unit);

      beamsSprites.put(enemyWeapon, enemyFireView);

      enemyFireView.add(getParent());
    }
  }

  private void setTask() {
    this.task = new Task<>() {
      @Override public Void call() {
        timer.start();

        final Timeline sleepFourSeconds = new Timeline(new KeyFrame(Duration.seconds(1), (actionEvent) -> enemyFireHandler()));
        sleepFourSeconds.setCycleCount(Timeline.INDEFINITE);
        sleepFourSeconds.play();

        return null;
      }
    };
  }

  private void startThread() {
    this.thread = new Thread(task);
    this.thread.start();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    this.unit = getUnit();
    
    controller
          .getModel()
          .getGrid()
          .entrySet()
          .stream()
          .filter(entry -> entry.getValue().isPresent())
          .forEachOrdered((entry) -> {
            final Entity entity = entry.getValue().get();
            final Pair<Float, Float> position = entry.getKey();
            final EntitySprite entitySprite = getStrategy(entity);
            
            entitySprite.create(position, entity, this.unit);
            entitySprite.add(getParent());
          }
    );
  
    AnchorPane.setBottomAnchor(getPlayerNode(),0.0);
    
    getParent().requestFocus();
  }

  /**
   * Ends the game while :
   * - goes back to the Menu View;
   * - deletes all the resources that are no longer used
   * - add gamePoints and player nickname to the ranking stats
   */
  public void endGame() {
    this.task.cancel();
    this.timer.stop();
    this.thread.interrupt();
    this.eventManager.cleanup();
  
    getStage().getScene().removeEventHandler(KeyEvent.KEY_PRESSED, this.keyHandler);
  
    controller.getModel().removePropertyChangeLister(this);
  
    getParent().getChildren().clear();
  
    MenuView.goToScene(CurrentScene.MENU);
  
    RankingController.addToRanking(controller.getPlayerName(), controller.getGamePoints());
  }
}
