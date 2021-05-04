package space.cstm.events;

import space.cstm.models.components.entities.CommonShip;

public class CommonShipHitEvent {
  public final CommonShip commonShip;
  
  public CommonShipHitEvent(final CommonShip commonShip) {
    this.commonShip = commonShip;
  }
}
