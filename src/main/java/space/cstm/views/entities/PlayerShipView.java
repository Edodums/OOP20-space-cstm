package space.cstm.views.entities;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import space.cstm.models.components.Collider;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.utils.Pair;
import space.cstm.views.sprite.EntitySprite;

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
  public void create(Pair<Float, Float> position, Entity entity, float unit) {
    EntitySprite.super.create(position, entity, unit);
    
    get().setId("player");
    get().setFitWidth(((Collider) entity).getWidth() * unit);
    get().setFitHeight(((Collider) entity).getHeight() * unit);
  }
  
  @Override
  public void update(Pair<Float, Float> position, float unit) {
    // empty
  }
  
  @Override
  public void add(Pane pane) {
    EntitySprite.super.add(pane);
  }
}
