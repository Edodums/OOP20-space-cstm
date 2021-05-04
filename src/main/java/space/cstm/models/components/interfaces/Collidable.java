package space.cstm.models.components.interfaces;

import space.cstm.models.components.Collider;
import space.cstm.models.components.entities.MotherShip;
import space.cstm.models.components.entities.PlayerShip;
import space.cstm.utils.ModelConstants;
import space.cstm.utils.Pair;

public interface Collidable {
    /**
     * I took into account that between the Entity position and the Weapon one there
     * could be a range of point that can be defined as the field of presence of them
     *
     * @param entity
     * @param deployedWeapon
     * @return true if the points are close enough to "hit" one another
     */
    default boolean hit(Collider entity, Collider deployedWeapon) {
        Pair<Float, Float> entityPosition = new Pair<>(entity.getPosition().getX(), entity.getPosition().getY());
        Pair<Float, Float> deployedWeaponPosition = new Pair<>(deployedWeapon.getPosition().getX(), deployedWeapon.getPosition().getY());
        
        float range = ModelConstants.ENTITY_RANGE;
        
        if (entity instanceof PlayerShip) {
            range = ModelConstants.PLAYER_RANGE;
        }

        return entityPosition.getX() < (deployedWeaponPosition.getX() + range) &&
              (entityPosition.getX() + range) > deployedWeaponPosition.getX() &&
                entityPosition.getY() < (deployedWeaponPosition.getY() + range) &&
              (entityPosition.getY() + range) > deployedWeaponPosition.getY()  ;
    }
}
