package space.cstm.events;

import space.cstm.models.Game;
import space.cstm.models.components.entities.CommonShip;
import space.cstm.models.components.entities.MotherShip;
import space.cstm.models.components.entities.PlayerShip;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.views.GameView;
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

    gameModel.setGamePoints(gameModel.getGamePoints() + commonShip.getPointsValue());
    gameModel.setAliveEnemies(gameModel.getAliveEnemies() - 1);
    
    if (gameModel.getAliveEnemies() == 0) {
      gameView.endGame();
    }
  }
  
  @Subscribe
  public void onMotherShipHit(MotherShipHitEvent motherShipHitEvent) {
    final MotherShip motherShip = motherShipHitEvent.motherShip;
    
    gameModel.setGamePoints(gameModel.getGamePoints() + motherShip.getPointsValue());
    gameModel.setAliveEnemies(gameModel.getAliveEnemies() - 1);
    
    if (gameModel.getAliveEnemies() == 0) {
      gameView.endGame();
    }
  }
  
  @Subscribe
  public void onPlayerShipHit(PlayerShipHitEvent playerShipHitEvent) {
    final PlayerShip playerShip = playerShipHitEvent.playerShip;
    
    gameModel.setGamePoints(gameModel.getGamePoints() - playerShip.getPointsValue());
    playerShip.setCurrentLives(playerShip.getCurrentLives() - 1);
    
    if (playerShip.getCurrentLives() == 0) {
      gameView.endGame();
    }
  }
   
  public void cleanup() {
    EventBus.getDefault().unregister(this);
  }
}
