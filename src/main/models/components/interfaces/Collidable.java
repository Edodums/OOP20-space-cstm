package main.models.components.interfaces;

import main.models.components.Collider;
import main.utils.Pair;

public interface Collidable {
    default boolean hit(Collider entity, Collider deployedWeapon) {
        Pair<Double, Double> entityPosition = entity.getPosition();
        Pair<Double, Double> deployedWeaponPosition = deployedWeapon.getPosition();

        return entityPosition.getX() < (deployedWeaponPosition.getX() + deployedWeapon.getWidth()) &&
              (entityPosition.getX() + entity.getWidth()) > deployedWeaponPosition.getX() &&
                entityPosition.getY() < (deployedWeaponPosition.getY() + deployedWeapon.getHeight()) &&
              (entityPosition.getY() + entity.getHeight()) > deployedWeaponPosition.getY()  ;
    }
}
