package space.cstm.views.entities;

import javafx.scene.image.ImageView;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.utils.Pair;
import space.cstm.views.entities.interfaces.Animated;
import space.cstm.views.sprite.EntitySprite;

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
  public void create(Pair<Float, Float> position, Entity entity, float unit) {
    EntitySprite.super.create(position, entity, unit);
    animate(get());
  }
}
