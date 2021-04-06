package main.events;

import main.models.Game;
import main.models.components.entities.CommonShip;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EventManager {
  private final Game gameModel = new Game();
  
  public EventManager() {
    EventBus.getDefault().register(this);
  }
  
  public void onPlayerShoot(PlayerShootEvent playerShootEvent) {
    // TODO: check if this implementation makes sense and if it works
    gameModel.collisionHandler();
  }
   
  @Subscribe
  public void onCommonShipHit(CommonShipHitEvent commonShipHitEvent) {
    CommonShip commonShip = commonShipHitEvent.commonShip;
      
    gameModel.removeFromGrid(commonShip.getPosition());
    gameModel.setAliveEnemies(gameModel.getAliveEnemies() - 1);
    gameModel.setGamePoints(gameModel.getGamePoints() + commonShip.getPointsValue());
  }
   
  public void cleanup() {
    EventBus.getDefault().unregister(this);
  }
}
