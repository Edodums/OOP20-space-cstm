package main.models.components.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import main.events.CommonShipHitEvent;
import main.models.EntityImage;
import main.models.components.Collider;
import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Entity;
import main.utils.Pair;
import org.greenrobot.eventbus.EventBus;
import static main.models.Game.*;

public class CommonShip extends Collider implements Entity, Collidable {
  private static final Pair<Double, Double> STARTING_POINT = new Pair<>(3.0, 1.0);
  private static final double WIDTH = 3.0;
  private static final double HEIGHT = 2.0;
  
  private final EntityImage entityImage;
  
  public CommonShip(EntityImage entityImage) {
    super();
    this.entityImage = entityImage;
  }
  
  @Override
  public void fire() {
    // empty
  }
  
  @Override
  public void die() {
    EventBus.getDefault().post(new CommonShipHitEvent(this));
  }
  
  @Override
  public double getPointsValue() {
    return getPosition().getY() % getEnemiesRows() * 10;
  }
  
  @Override
  public EntityImage getEntityImage() {
    return entityImage;
  }
  
  @Override
  public Map<Pair<Double, Double>, Optional<Entity>> create() {
    Map<Pair<Double, Double>, Optional<Entity>> army = new HashMap<>();
    
    IntStream.range(0, (int) getEnemiesColumns())
            .boxed()
            .flatMap(x -> IntStream.range(0, (int) getEnemiesRows())
                                  .mapToObj(y -> {
                                    double positionX = x + getStartingPoint().getX();
                                    double positionY = y + getStartingPoint().getY();
                      
                                    return new Pair<>(positionX, positionY);
                                  }))
            .forEach(pair -> army.put(pair, Optional.of(this)));
    
    return army;
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
  public double getSpawnNumber() {
    return getEnemiesColumns() * getEnemiesNextRows();
  }
  
  public Pair<Double, Double> getStartingPoint() {
    return STARTING_POINT;
  }
}
