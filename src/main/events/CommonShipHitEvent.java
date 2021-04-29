package main.events;

import main.models.components.entities.CommonShip;

public class CommonShipHitEvent {
  public final CommonShip commonShip;
  
  public CommonShipHitEvent(final CommonShip commonShip) {
    this.commonShip = commonShip;
  }
}
