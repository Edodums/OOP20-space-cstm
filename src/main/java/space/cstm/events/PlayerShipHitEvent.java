package space.cstm.events;

import space.cstm.models.components.entities.PlayerShip;

public class PlayerShipHitEvent {
  public final PlayerShip playerShip;
  
  public PlayerShipHitEvent(PlayerShip playerShip) {
    this.playerShip = playerShip;
  }
}
