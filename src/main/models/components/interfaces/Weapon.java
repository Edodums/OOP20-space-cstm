package main.models.components.interfaces;

import main.models.components.Collider;
import main.models.settings.interfaces.CustomizableTypeImage;
import main.utils.Pair;

public interface Weapon {
    
    void deploy(Pair<Float, Float> startingPoint);
    
    void move();
    
    boolean checkCollision(Collider entityToCheck);

    default String getFilename(){
        return getTypeImages().getName();
    }

    CustomizableTypeImage getTypeImages();
}
