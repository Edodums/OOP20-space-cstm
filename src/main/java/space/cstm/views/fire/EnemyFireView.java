package space.cstm.views.fire;

import javafx.scene.image.ImageView;
import space.cstm.models.components.interfaces.Weapon;
import space.cstm.utils.Pair;
import space.cstm.views.entities.interfaces.Animated;
import space.cstm.views.sprite.WeaponSprite;

public class EnemyFireView implements WeaponSprite, Animated {
  private ImageView imageView;
  
  public EnemyFireView() {}
  
  @Override
  public ImageView get() {
    return this.imageView;
  }
  
  @Override
  public void set(ImageView imageView) {
    this.imageView = imageView;
  }
  
  @Override
  public void create(Pair<Float, Float> position, Weapon weapon, float unit) {
    WeaponSprite.super.create(position, weapon, unit);
    animate(get());
  }
}