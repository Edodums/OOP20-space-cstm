package space.cstm.views.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import space.cstm.models.components.Collider;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.models.settings.interfaces.GridImage;
import space.cstm.utils.Pair;


public interface EntitySprite extends Sprite {
    /* Duplicated code, I didn't model Eniity and Weapon to have common , that's what I get */
    default void create(Pair<Float, Float> position, Entity entity, float unit) {
        final GridImage grid = entity.getTypeImages().getGrid();

        if (isSpriteDividedIntoGrid(grid)) {
            set(new ImageView(entity.getFilename()));
        } else {
            final Image[] images = getImages(entity.getTypeImages());
            handleSpriteImage(grid, position, images, unit);
        }

        Collider collider = (Collider) entity;

        get().setFitHeight(collider.getHeight() * unit );
        get().setFitWidth(collider.getWidth() * unit );
    }
}
