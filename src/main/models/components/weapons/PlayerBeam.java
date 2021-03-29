package main.models.components.weapons;

import main.models.components.Collider;
import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Weapon;
import main.utils.Pair;

public class PlayerBeam extends Collider implements Weapon, Collidable {
  private static final double WIDTH = 2.5;
  private static final double HEIGHT = 5.0;
  
  @Override
  public void deploy(Pair<Integer, Integer> startingPoint, Collider entityToCheck) {
    int currentY = getPosition() != null ? getPosition().getY() - 1 : startingPoint.getY() - 1;
    
    while (getPosition().getY() > 0) {
      setPosition(new Pair<>(startingPoint.getX(), currentY));
      
      if (hit(entityToCheck, this)) {
         // TODO: Event Manager event
      }
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
