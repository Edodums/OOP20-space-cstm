package main.views.entities;

import javafx.scene.image.ImageView;
import main.models.components.Collider;
import main.models.components.interfaces.Entity;
import main.utils.Pair;
import main.views.entities.interfaces.EntitySprite;

public class PlayerShipView implements EntitySprite {
  private ImageView playerShip;
  
  @Override
  public ImageView get() {
    return this.playerShip;
  }
  
  @Override
  public void set(ImageView playerShip) {
    this.playerShip = playerShip;
  }
  
  @Override
  public void create(Pair<Double, Double> position, Entity entity, double unit) {
    EntitySprite.super.create(position, entity, unit);
    get().setFitWidth(((Collider) entity).getWidth() * unit);
    get().setFitHeight(((Collider) entity).getHeight() * unit);
  }
}
