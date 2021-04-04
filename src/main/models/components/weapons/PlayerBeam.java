package main.models.components.weapons;

import main.models.components.Collider;
import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Entity;
import main.models.components.interfaces.Weapon;
import main.utils.Pair;

public class PlayerBeam extends Collider implements Weapon, Collidable {
  private static final double WIDTH = 2.5;
  private static final double HEIGHT = 5.0;
  
  @Override
  public void deploy(Pair<Integer, Integer> startingPoint) {
    int currentY = getPosition() != null ? getPosition().getY() - 1 : startingPoint.getY() - 1;
    
    while (getPosition().getY() > 0) {
      setPosition(new Pair<>(startingPoint.getX(), currentY));
    }
  }
  
  public void checkCollision(Collider entityToCheck) {
    if (hit(entityToCheck, this) && entityToCheck instanceof Entity) {
      ((Entity) entityToCheck).die();
    }
  }
  
  @Override
  public double getWidth() {
    return WIDTH;
  }
  
  @Override
  public double getHeight() {
    return HEIGHT;
  }
}
