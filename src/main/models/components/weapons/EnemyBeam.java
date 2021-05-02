package main.models.components.weapons;

import main.models.components.Collider;
import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Entity;
import main.models.components.interfaces.Weapon;
import main.models.settings.interfaces.CustomizableTypeImage;
import main.utils.ModelConstants;
import main.utils.Pair;

public class EnemyBeam extends Collider implements Weapon, Collidable {
  private static final float WIDTH = 0.25625f;
  private static final float HEIGHT = 0.6125f;
  
  private final CustomizableTypeImage entityImage;
  
  public EnemyBeam(CustomizableTypeImage entityImage) {
    super();
    this.entityImage = entityImage;
  }
  
  @Override
  public void deploy(Pair<Float, Float> startingPoint) {
    final float translateYUnit = ModelConstants.ENEMY_BEAM_TRANSLATE_Y_UNIT;
    final float translateXUnit = ModelConstants.ENEMY_BEAM_TRANSLATE_X_UNIT;
    final float currentX = startingPoint.getX() + translateXUnit;
    final float currentY = startingPoint.getY() - translateYUnit;
  
    setPosition(new Pair<>(currentX, currentY));
  }
  
  @Override
  public void move() {
    if (getPosition() != null && getPosition().getY() > 0) {
      final float translateYUnit = ModelConstants.ENEMY_BEAM_MOVE_Y_UNIT;
      setPosition(new Pair<>(getPosition().getX(), getPosition().getY() + translateYUnit));
    }
  }
  
  @Override
  public boolean checkCollision(Collider entityToCheck) {
    if (hit(entityToCheck, this) && entityToCheck instanceof Entity) {
      ((Entity) entityToCheck).die(entityToCheck);
      return true;
    }
    
    return false;
  }
  
  @Override
  public CustomizableTypeImage getTypeImages() {
    return entityImage;
  }
  
  @Override
  public float getWidth() {
    return WIDTH;
  }
  
  @Override
  public float getHeight() {
    return HEIGHT;
  }
  
  @Override
  public Pair<Float, Float> getStartingPoint() {
    return null;
  }
}
