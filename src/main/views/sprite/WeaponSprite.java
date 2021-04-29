package main.views.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.models.components.Collider;
import main.models.components.interfaces.Weapon;
import main.models.settings.Grid;
import main.utils.Pair;


public interface WeaponSprite extends Sprite {
  default void create(Pair<Float, Float> position, Weapon weapon, float unit) {
    final Grid grid = weapon.getTypeImages().getGrid();
    
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
