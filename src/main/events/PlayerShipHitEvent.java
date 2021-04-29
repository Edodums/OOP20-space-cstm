package main.events;

import main.models.components.entities.PlayerShip;
import main.models.components.interfaces.Weapon;

public class PlayerShipHitEvent {
  public final PlayerShip playerShip;
  public final Weapon weaponToRemove;
  
  public PlayerShipHitEvent(PlayerShip playerShip, Weapon weaponToRemove) {
    this.playerShip = playerShip;
    this.weaponToRemove = weaponToRemove;
  }
}
