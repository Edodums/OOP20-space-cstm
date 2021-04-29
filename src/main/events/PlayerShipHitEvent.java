package main.events;

import main.models.components.entities.PlayerShip;

public class PlayerShipHitEvent {
  public final PlayerShip playerShip;
  
  public PlayerShipHitEvent(PlayerShip playerShip) {
    this.playerShip = playerShip;
  }
}
