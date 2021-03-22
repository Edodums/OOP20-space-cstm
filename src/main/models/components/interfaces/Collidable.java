package main.models.components.interfaces;

public interface Collidable {
    boolean wasHit(Entity entity, Weapon deployedWeapon);
}
