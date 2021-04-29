package main.events;

import main.models.components.entities.MotherShip;

public class MotherShipHitEvent {
  public final MotherShip motherShip;
  
  public MotherShipHitEvent(MotherShip motherShip) {
    this.motherShip = motherShip;
  }
}
