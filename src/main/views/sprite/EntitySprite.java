package main.views.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.models.components.interfaces.Entity;
import main.models.settings.Grid;
import main.utils.Pair;


public interface EntitySprite extends Sprite {
    default void create(Pair<Float, Float> position, Entity entity, float unit) {
        final Grid grid = entity.getTypeImages().getGrid();
        
        if (isSpriteDividedIntoGrid(grid)) {
            set(new ImageView(entity.getFilename()));
        } else {
            final Image[] images = getImages(entity.getTypeImages());
            handleSpriteImage(grid, position, images, unit);
        }
    }
}
