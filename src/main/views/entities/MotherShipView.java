package main.views.entities;

import javafx.scene.image.ImageView;
import main.views.entities.interfaces.EntitySprite;

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
