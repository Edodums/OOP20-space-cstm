package main.models.components.interfaces;

import main.models.components.Collider;
import main.models.settings.TypeImage;
import main.utils.Pair;

public interface Weapon {
    
    void deploy(Pair<Float, Float> startingPoint);
    
    void move();
    
    boolean checkCollision(Collider entityToCheck);
    
    TypeImage getTypeImages();
    
    default String getFilename(){
        return getTypeImages().getName();
    }
    
}
