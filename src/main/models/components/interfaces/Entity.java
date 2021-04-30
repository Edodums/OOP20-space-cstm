package main.models.components.interfaces;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import main.events.PlayerShipHitEvent;
import main.models.components.entities.PlayerShip;
import main.models.components.Collider;
import main.models.components.entities.MotherShip;
import main.models.components.entities.CommonShip;
import main.models.settings.interfaces.CustomizableTypeImage;
import main.utils.Pair;
import org.greenrobot.eventbus.EventBus;

import static main.models.Game.*;


public interface Entity {
    
    Map<Pair<Float, Float>, Optional<Entity>> create();

    void die(Collider entityToCheck);

    CustomizableTypeImage getTypeImages();

    float getPointsValue();

    float getSpawnNumber();

    default boolean canEntityMove(float x, float y) {
        return x < getMaxX() && y < getMaxY();
    }

    default boolean isNPC() {
        return this instanceof CommonShip || this instanceof MotherShip;
    }
    
    default boolean isPlayer() {
        return this instanceof PlayerShip;
    }
    
    default void move(Pair<Float, Float> pair, boolean isRight) {}

    default void move(Collider entity, Pair<Float, Float> pair, Optional<Entity> player) {
        final float unit = 0.014f;
        final float accelerationFactor = getAccelerationFactor(pair);
        final float minX = 1 + unit;
        final float minY = entity.getStartingPoint().getY();
        final float maxX = getMaxX() - unit;
        final float maxY = minY + getEnemiesNextRows() + getEnemiesRows();
        
        
        /* 1. if you've reach the end you set take away 1 life from player counter. */
        if (pair.getY() >= maxY  && player.isPresent()) {
            EventBus.getDefault().post(new PlayerShipHitEvent((PlayerShip) player.get()));
            System.out.println("REACHING END");
        }

        final float addX = pair.getX() + unit + accelerationFactor;
        
        /* 2. if you've to go right then check if the current number (x) is lower than the next one */
        if (addX <= maxX  && isGoingRight(pair.getY(), minY)) {
            // System.out.println("GOING RIGHT: " + pair + " CLASS:" + entity);
            pair.setX(addX);
        }
    
        final float minusX = pair.getX() - unit - accelerationFactor;
    
       // System.out.println((minusX >= minX)  + " xoxo  " + (!isGoingRight(pair.getY(), minY)));
        /* 3. if you've to go left then check if the current number (x) is greater than the next one */
        if (minusX >= minX && !isGoingRight(pair.getY(), minY)) {
            // System.out.println("COMING RIGHT: " + pair + " CLASS:" + entity);
            pair.setX(minusX);
        }
        
        /* 4. if you reach the last column and you're coming from right go down  */
        if (round(pair.getX(), 1 , true) >= maxX && isGoingRight(pair.getY(), minY))  {
            // System.out.println("LAST COLUMN GOING RIGHT: " + pair + " CLASS:" + entity);
            pair.setY(pair.getY() + 0.2f);
        }
    
        
        // System.out.println("pair getx: " + pair.getX() + " minx: " + minX + " !isGoingRight: " + !isGoingRight(pair.getY(), minY) + " pairgetY: " +  pair.getY() + "  round: " + round(pair.getX(), 1, false));
        /* 5. if you reach the first column and you're coming from left then go down */
        if  (round(pair.getX(), 1, false) <= minX && !isGoingRight(pair.getY(), minY)) {
            // System.out.println("FIRST COLUMN GOING RIGHT: " + pair + " CLASS:" + entity);
            pair.setY(pair.getY() + 0.2f);
        }
        
        
        entity.setPosition(pair);
    }
    
    default float round(float number, int scale, boolean isUp) {
        RoundingMode roundingMode = RoundingMode.UP;
        
        if (!isUp) {
            roundingMode = RoundingMode.DOWN;
        }
    
        return BigDecimal.valueOf(number).setScale(scale, roundingMode).floatValue();
    }

    default float getAccelerationFactor(Pair<Float, Float> pair) {
        return pair.getY() % getMaxX() * 0.0025f;
    }

    default boolean isGoingRight(float currentY, float startingY) {
        final float subtract = currentY - startingY;
        
        if (subtract == 0)  {
            return true;
        }
        
        return round(subtract, 0, true) % 2.0 == 0.0;
    }

    default String getFilename(){
        return getTypeImages().getName();
    }
}
