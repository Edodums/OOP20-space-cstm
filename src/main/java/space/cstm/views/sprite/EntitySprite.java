package space.cstm.views.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.models.settings.interfaces.GridImage;
import space.cstm.utils.Pair;


public interface EntitySprite extends Sprite {
    default void create(Pair<Float, Float> position, Entity entity, float unit) {
        final GridImage grid = entity.getTypeImages().getGrid();
        
        if (isSpriteDividedIntoGrid(grid)) {
            set(new ImageView(entity.getFilename()));
        } else {
            final Image[] images = getImages(entity.getTypeImages());
            handleSpriteImage(grid, position, images, unit);
        }
    }
}
