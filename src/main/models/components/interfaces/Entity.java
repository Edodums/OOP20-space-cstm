package main.models.components.interfaces;

import main.models.settings.TypeImage;
import main.models.components.Collider;
import main.models.components.entities.MotherShip;
import main.models.components.entities.CommonShip;
import main.utils.Pair;

import java.util.Map;
import java.util.Optional;

import static main.models.Game.*;


public interface Entity {
    
    Map<Pair<Double, Double>, Optional<Entity>> create();

    void fire();

    void die();

    TypeImage getTypeImages();

    double getPointsValue();

    double getSpawnNumber();

    default boolean canEntityMove(Double x, Double y) {
        return x < getMaxX() && y < getMaxY();
    }

    default boolean isNPC() {
        return this instanceof CommonShip || this instanceof MotherShip;
    }

    default void move(Collider entity, Pair<Double, Double> pair) {
        double unit = 1;
        double accelerationFactor = 0;
    
        // System.out.println("INIT: " + pair);
        
        /* 1. if you've reach the end you set take away 1 life from player counter. */
        if (pair.getX() >= getEnemiesColumns() && pair.getY() >= (getEnemiesRows() + getEnemiesNextRows())) {
            //TODO: probably I'll handle it with an Event ( if so, @Arianna have to create an Event Manager )
            // System.out.println("REACHING END");
        }

        /* 2. if you've to go right then check if the current number (x) is lower than the next one */
        if (!isComingRight(pair.getY())) {
            // System.out.println("GOING RIGHT: " + pair + " CLASS:" + entity);
            pair.setX(pair.getX() + unit + accelerationFactor);
        }

        /* 3. if you've to go left then check if the current number (x) is greater than the next one */
        if (isComingRight(pair.getY())) {
            // System.out.println("COMING RIGHT: " + pair + " CLASS:" + entity);
            pair.setX(pair.getX() - unit - accelerationFactor);
        }
    
        /* 4. if you reach the last column / or the first one, go down  */
        if (pair.getX() >= getMaxX() || pair.getX() <= entity.getStartingPoint().getX())  {
            // System.out.println("LAST COLUMN GOING RIGHT: " + pair + " CLASS:" + entity);
            pair.setY(pair.getY() + unit);
        }
    
        // System.out.println("FINISH: " + pair);
        entity.setPosition(pair);
    }

    default double getAccelerationFactor(Pair<Double, Double> pair) {
        return pair.getY() % getMaxX() * 0.25;
    }

    default boolean isComingRight(Double currentY) {
        return currentY % 2 == 0;
    }

    default String getFilename(){
        return getTypeImages().getName();
    }
}
