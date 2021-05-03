package space.cstm.models.components.interfaces;

import space.cstm.models.components.Collider;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.utils.Pair;

public interface Weapon {
    
    void deploy(Pair<Float, Float> startingPoint);
    
    void move();
    
    boolean checkCollision(Collider entityToCheck);

    default String getFilename(){
        return getTypeImages().getName();
    }

    CustomizableTypeImage getTypeImages();
}
