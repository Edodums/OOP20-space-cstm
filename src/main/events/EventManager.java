package main.events;

import main.models.Game;
import main.models.components.entities.CommonShip;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EventManager {
  private final Game gameModel;
  
  public EventManager(final Game gameModel) {
    this.gameModel = gameModel;
    EventBus.getDefault().register(this);
  }
  
  @Subscribe
  public void onPlayerShoot(PlayerShootEvent playerShootEvent) {
    // TODO: check if this implementation makes sense and if it works
    gameModel.collisionHandler();
  }
  @Subscribe
  public void onPlayerGoRight(PlayerGoRightEvent playerGoRightEvent) {
    playerGoRightEvent.goRight();
  }
  
  @Subscribe
  public void onPlayerGoLeft(PlayerGoLeftEvent playerGoLeftEvent) {
    playerGoLeftEvent.goLeft();
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
