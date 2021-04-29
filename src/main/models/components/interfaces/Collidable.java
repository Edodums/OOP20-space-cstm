package main.models.components.interfaces;

import main.models.components.Collider;
import main.utils.Pair;

public interface Collidable {
    default boolean hit(Collider entity, Collider deployedWeapon) {
        Pair<Float, Float> entityPosition = new Pair<>(entity.getPosition().getX(), entity.getPosition().getY());
        Pair<Float, Float> deployedWeaponPosition = new Pair<>(deployedWeapon.getPosition().getX(), deployedWeapon.getPosition().getY());
        final float range = 0.20f;
        
        return entityPosition.getX() < (deployedWeaponPosition.getX() + range) &&
              (entityPosition.getX() + range) > deployedWeaponPosition.getX() &&
                entityPosition.getY() < (deployedWeaponPosition.getY() + range) &&
              (entityPosition.getY() + range) > deployedWeaponPosition.getY()  ;
    }
}
