package main.events;

import main.models.Game;
import main.models.components.entities.CommonShip;
import main.models.components.entities.MotherShip;
import main.models.components.entities.PlayerShip;
import main.models.components.interfaces.Weapon;
import main.views.GameView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EventManager {
  private final Game gameModel;
  private final GameView gameView;
  
  public EventManager(final Game gameModel, GameView gameView) {
    this.gameModel = gameModel;
    this.gameView = gameView;
    EventBus.getDefault().register(this);
  }
  
  @Subscribe
  public void onCommonShipHit(CommonShipHitEvent commonShipHitEvent) {
    final CommonShip commonShip = commonShipHitEvent.commonShip;
    final Weapon weapon = commonShipHitEvent.weaponToRemove;
      
    gameModel.removeFromGrid(commonShip);
    gameModel.setAliveEnemies(gameModel.getAliveEnemies() - 1);
    gameModel.setGamePoints(gameModel.getGamePoints() + commonShip.getPointsValue());
    gameModel.removeLaserInstance(weapon);
    
    if (gameModel.getAliveEnemies() == 0) {
      gameView.endGame();
    }
  }
  
  @Subscribe
  public void onMotherShipHit(MotherShipHitEvent motherShipHitEvent) {
    final MotherShip motherShip = motherShipHitEvent.motherShip;
    final Weapon weapon = motherShipHitEvent.weaponToRemove;
    
    gameModel.removeFromGrid(motherShip);
    gameModel.setAliveEnemies(gameModel.getAliveEnemies() - 1);
    gameModel.setGamePoints(gameModel.getGamePoints() + motherShip.getPointsValue());
    gameModel.removeLaserInstance(weapon);
    
    if (gameModel.getAliveEnemies() == 0) {
      gameView.endGame();
    }
  }
  
  @Subscribe
  public void onPlayerShipHit(PlayerShipHitEvent playerShipHitEvent) {
    final PlayerShip playerShip = playerShipHitEvent.playerShip;
    final Weapon weapon = playerShipHitEvent.weaponToRemove;
    
    gameModel.setGamePoints(gameModel.getGamePoints() - playerShip.getPointsValue());
    gameModel.removeLaserInstance(weapon);
  
    if (playerShip.getCurrentLives() <= 0) {
      gameModel.removeFromGrid(playerShip);
      gameView.endGame();
    } else {
      playerShip.setCurrentLives(playerShip.getCurrentLives() - 1);
    }
  }
   
  public void cleanup() {
    EventBus.getDefault().unregister(this);
  }
}
