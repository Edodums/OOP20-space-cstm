package main.events;

import main.models.components.entities.CommonShip;
import main.models.components.interfaces.Weapon;

public class CommonShipHitEvent {
  public final CommonShip commonShip;
  public final Weapon weaponToRemove;
  
  public CommonShipHitEvent(CommonShip commonShip, Weapon weaponToRemove) {
    this.commonShip = commonShip;
    this.weaponToRemove = weaponToRemove;
  }
}
