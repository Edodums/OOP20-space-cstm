package main.models.components.entities;

import main.models.components.Collider;
import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Entity;
import main.models.settings.TypeImage;
import main.utils.Pair;

import java.util.Map;
import java.util.Optional;

import static main.models.Game.getMaxX;

public class PlayerShip extends Collider implements Entity, Collidable {
  private static final Pair<Double, Double> STARTING_POINT = new Pair<>(3.0, getMaxX());
  private static final double POINTS = -50;
  private static final double SPAWN_NUMBER = 1;
  private static final double WIDTH = 3.5;
  private static final double HEIGHT = 3.0;
  
  private final TypeImage typeImage;
  
  public PlayerShip(TypeImage typeImage) {
    super();
    this.typeImage = typeImage;
  }
  
  @Override
  public double getWidth() {
    return WIDTH;
  }
  
  @Override
  public double getHeight() {
    return HEIGHT;
  }
  
  @Override
  public Pair<Double, Double> getStartingPoint() {
    return STARTING_POINT;
  }
  
  @Override
  public Map<Pair<Double, Double>, Optional<Entity>> create() {
    setPosition(STARTING_POINT);
    return Map.of(STARTING_POINT, Optional.of(this));
  }
  
  @Override
  public void move(Collider entity, Pair<Double, Double> pair) {
    
  }
  
  @Override
  public void fire() {
    // handle fire event
  }
  
  @Override
  public void die() {
    // handle die
  }
  
  @Override
  public TypeImage getTypeImages() {
    return this.typeImage;
  }
  
  @Override
  public double getPointsValue() {
    return POINTS;
  }
  
  @Override
  public double getSpawnNumber() {
    return SPAWN_NUMBER;
  }
}
