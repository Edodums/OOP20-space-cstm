package main.views.entities;

import javafx.scene.image.ImageView;
import main.models.components.interfaces.Entity;
import main.utils.Pair;
import main.views.entities.interfaces.Animated;
import main.views.entities.interfaces.EntitySprite;

public class CommonShipView implements EntitySprite, Animated  {
  private ImageView commonShip;
  
  @Override
  public ImageView get() {
    return this.commonShip;
  }
  
  @Override
  public void set(ImageView commonShip) {
    this.commonShip = commonShip;
  }
  
  @Override
  public void create(Pair<Double, Double> position, Entity entity, double unit) {
    EntitySprite.super.create(position, entity, unit);
    animate(get());
  }
}
