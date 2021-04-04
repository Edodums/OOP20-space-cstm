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

public class CommonShip extends Collider implements Entity, Collidable {
  private static final Integer COLUMNS = 8;
  private static final Integer ROWS = 3;
  private static final Integer NEXT_ROWS = 6;
  private static final Pair<Integer, Integer> STARTING_POINT = new Pair<>(3, 1);
  
  private static final double WIDTH = 3.0;
  private static final double HEIGHT = 2.0;
  
  private final EntityImage entityImage;
  
  public CommonShip(EntityImage entityImage) {
    super();
    this.entityImage = entityImage;
  }
  
  @Override
  public void move() {
    Pair<Integer, Integer> newPair = null;
    
    /* 1. if you've reach the end you set take away 1 life from player counter. */
    if (getPosition().getX()
                     .equals(getCommonEnemiesColumns())
                && getPosition().getY()
                                .equals(getCommonEnemiesRows() + getCommonEnemiesNextRows())) {
      //TODO: probably I'll handle it with an Event ( if so, @Arianna have to create an Event Manager )
      System.out.println("REACHING END");
      return;
    }
    
    /* 2. if you've to go right then check if the
          current number (x) is lower than the next one */
    if (!isComingRight(getPosition().getY())) {
      newPair = new Pair<>(getPosition().getX() + 1, getPosition().getY());
    }
    
    /* 3. if you've to go left then check if the
          current number (x) is greater than the next one */
    if (isComingRight(getPosition().getY())) {
      newPair = new Pair<>(getPosition().getX() - 1, getPosition().getY());
    }
    
    /* 4. if you reach the last column then go down
          and back (x - 1) && check if coming from the left */
    if (getPosition().getX()
                     .equals(getCommonEnemiesColumns()) && !isComingRight(getPosition().getY())) {
      newPair = new Pair<>(getPosition().getX() - 1, getPosition().getY() + 1);
    }
    
    /* 5. if you reach the first column then go down
          and forth ( x + 1 ) && check if coming from the right */
    if (getPosition().getX()
                     .equals(getStartingPoint().getX()) && isComingRight(getPosition().getY())) {
      newPair = new Pair<>(getPosition().getX() + 1, getPosition().getY() + 1);
    }
    
    if (newPair == null) {
      throw new NullPointerException("Not really an expected scenario");
    }
    
    setPosition(newPair);
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
  public String getFilename() {
    return this.entityImage.getName();
  }
  
  @Override
  public String getSpriteType() {
    return this.entityImage.getSpriteType();
  }
  
  @Override
  public Map<Pair<Integer, Integer>, Optional<Entity>> create() {
    Map<Pair<Integer, Integer>, Optional<Entity>> army = new HashMap<>();
    
    IntStream.range(0, getCommonEnemiesColumns())
            .boxed()
            .flatMap(x -> IntStream.range(0, getCommonEnemiesRows())
                                  .mapToObj(y -> {
                                    int positionX = x + getStartingPoint().getX();
                                    int positionY = y + getStartingPoint().getY();
                      
                                    return new Pair<>(positionX, positionY);
                                  }))
            .forEach(pair -> army.put(pair, Optional.of(this)));
    
    return army;
  }
  
  private boolean isComingRight(Integer currentY) {
    return currentY % 2 == 0;
  }
  
  private static Integer getCommonEnemiesColumns() {
    return COLUMNS;
  }
  
  private static Integer getCommonEnemiesRows() {
    return ROWS;
  }
  
  public static Integer getCommonEnemiesNextRows() {
    return NEXT_ROWS;
  }
  
  private static Pair<Integer, Integer> getStartingPoint() {
    return STARTING_POINT;
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
  public Integer getSpawnNumber() {
    return getCommonEnemiesColumns() * getCommonEnemiesRows();
  }
  
  @Override
  public Integer getPointsValue() {
     return getPosition().getY() % getCommonEnemiesRows() * 10;
  }
}
