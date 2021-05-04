package space.cstm.views.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import space.cstm.models.components.Collider;
import space.cstm.models.components.interfaces.Weapon;
import space.cstm.models.settings.interfaces.GridImage;
import space.cstm.utils.Pair;


public interface WeaponSprite extends Sprite {
  default void create(Pair<Float, Float> position, Weapon weapon, float unit) {
    final GridImage grid = weapon.getTypeImages().getGrid();
    
    if (isSpriteDividedIntoGrid(grid)) {
      set(new ImageView(weapon.getFilename()));
    } else {
      final Image[] images = getImages(weapon.getTypeImages());
      handleSpriteImage(grid, position, images, unit);
    }
    
    Collider collider = (Collider) weapon;
    
    get().setFitHeight(collider.getHeight() * unit);
    get().setFitWidth(collider.getWidth() * unit);
  }
}
