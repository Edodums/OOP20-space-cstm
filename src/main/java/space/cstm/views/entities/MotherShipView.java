package space.cstm.views.entities;

import javafx.scene.image.ImageView;
import space.cstm.views.sprite.EntitySprite;

public class MotherShipView implements EntitySprite {
  private ImageView playerShip;
  
  @Override
  public ImageView get() {
    return this.playerShip;
  }
  
  @Override
  public void set(ImageView playerShip) {
    this.playerShip = playerShip;
  }
}
