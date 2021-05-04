package space.cstm.models.components.interfaces;

import space.cstm.models.components.Collider;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.utils.Pair;

public interface Weapon {
    /**
     * Sets the first position of a weapon
     * @param startingPoint
     */
    void deploy(Pair<Float, Float> startingPoint);

    /**
     * Translates of one unit in the Y axis the position of the weapon
     */
    void move();

    /**
     *
     * @param entityToCheck
     * @return true if a weapon has collided with a Weapon instance
     */
    boolean checkCollision(Collider entityToCheck);

    /**
     *
     * @return the file name of the image associated to the Weapon instance
     */
    default String getFilename(){
        return getTypeImages().getName();
    }

    /**
     *
     * @return the type image instance of the associated Weapon instance
     */
    CustomizableTypeImage getTypeImages();
}
