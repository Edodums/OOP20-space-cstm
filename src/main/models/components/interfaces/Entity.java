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
import main.utils.ModelConstants;
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
        final float unit = ModelConstants.ENTITY_MOVE_X_UNIT;
        final float accelerationFactor = getAccelerationFactor(pair);
        final float minX = ModelConstants.ENTITY_MIN_UNIT + unit;
        final float minY = entity.getStartingPoint().getY();
        final float maxX = getMaxX() - unit;
        final float maxY = minY + getEnemiesNextRows() + getEnemiesRows();
        
        
        /* 1. if you've reach the end you set take away 1 life from player counter. */
        if (pair.getY() >= maxY  && player.isPresent()) {
            EventBus.getDefault().post(new PlayerShipHitEvent((PlayerShip) player.get()));
        }

        final float addX = pair.getX() + unit + accelerationFactor;
        
        /* 2. if you've to go right then check if the current number (x) is lower than the next one */
        if (addX <= maxX  && isGoingRight(pair.getY(), minY)) {
            pair.setX(addX);
        }
    
        final float minusX = pair.getX() - unit - accelerationFactor;
        
        /* 3. if you've to go left then check if the current number (x) is greater than the next one */
        if (minusX >= minX && !isGoingRight(pair.getY(), minY)) {
            pair.setX(minusX);
        }
        
        /* 4. if you reach the last column and you're coming from right go down  */
        if (round(pair.getX(), ModelConstants.ENTITY_SCALE_UNIT , true) >= maxX && isGoingRight(pair.getY(), minY))  {
            pair.setY(pair.getY() + ModelConstants.ENTITY_MOVE_Y_UNIT);
        }
        
        /* 5. if you reach the first column and you're coming from left then go down */
        if  (round(pair.getX(), ModelConstants.ENTITY_SCALE_UNIT, false) <= minX && !isGoingRight(pair.getY(), minY)) {
            pair.setY(pair.getY() + ModelConstants.ENTITY_MOVE_Y_UNIT);
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
