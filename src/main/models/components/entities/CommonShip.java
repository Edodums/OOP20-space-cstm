package main.models.components.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import main.events.CommonShipHitEvent;
import main.models.components.Collider;
import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Entity;
import main.models.settings.interfaces.CustomizableTypeImage;
import main.utils.Pair;
import org.greenrobot.eventbus.EventBus;
import static main.models.Game.*;

public class CommonShip extends Collider implements Entity, Collidable {
  private static final Pair<Float, Float> STARTING_POINT = new Pair<>(3.0f, 1.0f);
  private static final float WIDTH = 10.0f;
  private static final float HEIGHT = 4.0f;
  
  private final CustomizableTypeImage entityImage;
  
  public CommonShip(CustomizableTypeImage entityImage) {
    super();
    this.entityImage = entityImage;
  }
  
  @Override
  public void die(Collider entityToCheck) {
    EventBus.getDefault().post(new CommonShipHitEvent((CommonShip) entityToCheck));
  }
  
  @Override
  public float getPointsValue() {
    return getPosition().getY() % getEnemiesRows() * 10;
  }
  
  @Override
  public CustomizableTypeImage getTypeImages() {
    return entityImage;
  }
  
  @Override
  public Map<Pair<Float, Float>, Optional<Entity>> create() {
    Map<Pair<Float, Float>, Optional<Entity>> army = new HashMap<>();
    
    IntStream.range(0, (int) getEnemiesColumns())
          .boxed()
          .flatMap(x -> IntStream.range(0, (int) getEnemiesRows()).mapToObj(y -> {
            final float positionX = x + STARTING_POINT.getX();
            final float positionY = y + STARTING_POINT.getY();
            
            return new Pair<>(positionX, positionY);
           }))
          .forEach(pair -> {
            CommonShip ship = new CommonShip(getTypeImages());
            
            ship.setPosition(pair);
            
            final Pair<Float, Float> startingPosition = new Pair<>(pair.getX(), pair.getY());
            
            ship.setStartingPosition(startingPosition);
            
            army.put(pair, Optional.of(ship));
          });
    
    return army;
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
  public float getSpawnNumber() {
    return getEnemiesColumns() * getEnemiesNextRows();
  }
}
