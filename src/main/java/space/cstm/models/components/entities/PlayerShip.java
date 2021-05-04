package space.cstm.models.components.entities;

import space.cstm.events.PlayerShipHitEvent;
import space.cstm.models.components.Collider;
import space.cstm.models.components.interfaces.Collidable;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.utils.Pair;
import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.Optional;
import static space.cstm.models.Game.getMaxY;

public class PlayerShip extends Collider implements Entity, Collidable {
  private static final Pair<Float, Float> STARTING_POINT = new Pair<>(3.0f, getMaxY());
  private static final float POINTS = -50;
  private static final float SPAWN_NUMBER = 1;
  private static final float WIDTH = 2.0f;
  private static final float HEIGHT = 1.8f;
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
  public void move(Pair<Float, Float> pair) {
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
