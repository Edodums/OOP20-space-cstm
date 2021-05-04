package space.cstm.models.components.interfaces;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import space.cstm.events.PlayerShipHitEvent;
import space.cstm.models.components.entities.PlayerShip;
import space.cstm.models.components.Collider;
import space.cstm.models.components.entities.MotherShip;
import space.cstm.models.components.entities.CommonShip;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.utils.ModelConstants;
import space.cstm.utils.Pair;
import org.greenrobot.eventbus.EventBus;

import static space.cstm.models.Game.*;


public interface Entity {
    /**
     *
     * @return the Entity instance ( instances in the case of CommonShip )
     */
    Map<Pair<Float, Float>, Optional<Entity>> create();

    /**
     * Launches the Event deletes the Entity from the grid
     * @param entityToCheck
     */
    void die(Collider entityToCheck);

    /**
     *
     * @return the type image
     */
    CustomizableTypeImage getTypeImages();

    /**
     *
     * @return
     */
    float getPointsValue();

    /**
     *
     * @return
     */
    float getSpawnNumber();

    /**
     *
     * @param x
     * @param y
     * @return
     */
    default boolean canEntityMove(float x, float y) {
        return x < getMaxX() && y < getMaxY();
    }

    /**
     *
     * @return
     */
    default boolean isNPC() {
        return this instanceof CommonShip || this instanceof MotherShip;
    }

    /**
     *
     * @return
     */
    default boolean isPlayer() {
        return this instanceof PlayerShip;
    }

    /**
     * Just a default method that I've put to avoid to use the other move
     * If I want, in the future, to make the playership not controlled by a user,
     * and instead autonomous I can implement it via this
     * @param pair
     */
    default void move(Pair<Float, Float> pair) {}

    /**
     *
     * @param entity
     * @param pair
     * @param player
     */
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

    /**
     *
     * @param number
     * @param scale
     * @param isUp
     * @return
     */
    default float round(float number, int scale, boolean isUp) {
        RoundingMode roundingMode = RoundingMode.UP;
        
        if (!isUp) {
            roundingMode = RoundingMode.DOWN;
        }
    
        return BigDecimal.valueOf(number).setScale(scale, roundingMode).floatValue();
    }

    /**
     *
     * @param pair
     * @return
     */
    default float getAccelerationFactor(Pair<Float, Float> pair) {
        return pair.getY() % getMaxX() * 0.0025f;
    }

    /**
     *
     * @param currentY
     * @param startingY
     * @return
     */
    default boolean isGoingRight(float currentY, float startingY) {
        final float subtract = currentY - startingY;
        
        if (subtract == 0)  {
            return true;
        }
        
        return round(subtract, 0, true) % 2.0 == 0.0;
    }

    /**
     *
     * @return
     */
    default String getFilename(){
        return getTypeImages().getName();
    }
}
