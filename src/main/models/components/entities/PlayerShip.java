package main.models.components.entities;

import main.models.components.Collider;
import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Entity;
import main.models.settings.TypeImage;
import main.models.settings.interfaces.CustomizableTypeImage;
import main.utils.Pair;
import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.Optional;

import static main.models.Game.getMaxX;
import static main.models.Game.getMaxY;

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

    public static class PlayerShip extends Collider implements Entity, Collidable {
      private static final Pair<Float, Float> STARTING_POINT = new Pair<>(3.0f, getMaxY());
      private static final float POINTS = -50;
      private static final float SPAWN_NUMBER = 1;
      private static final float WIDTH = 1.5f;
      private static final float HEIGHT = 1.0f;
      private static final int TOTAL_LIVES = 3;

      private final CustomizableTypeImage typeImage;

      private int currentLives = TOTAL_LIVES;

      public PlayerShip(CustomizableTypeImage typeImage) {
        super();
        this.typeImage = typeImage;
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
      public Map<Pair<Float, Float>, Optional<Entity>> create() {
        final Pair<Float, Float> startingPosition = STARTING_POINT;
        setStartingPosition(startingPosition);

        final Pair<Float, Float> position = new Pair<>(startingPosition.getX(), startingPosition.getY());
        setPosition(position);

        return Map.of(position, Optional.of(this));
      }

      @Override
      public void move(Pair<Float, Float> pair, boolean isRight) {
        // empty
      }

      @Override
      public void die(Collider entityToCheck) {
        EventBus.getDefault().post(new PlayerShipHitEvent((PlayerShip) entityToCheck));
      }

      @Override
      public CustomizableTypeImage getTypeImages() {
        return this.typeImage;
      }

      @Override
      public float getPointsValue() {
        return POINTS;
      }

      @Override
      public float getSpawnNumber() {
        return SPAWN_NUMBER;
      }

      public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
      }

      public int getCurrentLives() {
        return currentLives;
      }

      public static int getTotalLives() {
        return TOTAL_LIVES;
      }
    }
}
