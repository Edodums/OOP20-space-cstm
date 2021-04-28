package main.events;

import main.models.components.entities.MotherShip;
import main.models.components.interfaces.Weapon;

public class MotherShipHitEvent {
  public final MotherShip motherShip;
  public final Weapon weaponToRemove;
  
  public MotherShipHitEvent(MotherShip motherShip, Weapon weaponToRemove) {
    this.motherShip = motherShip;
    this.weaponToRemove = weaponToRemove;
  }
}
