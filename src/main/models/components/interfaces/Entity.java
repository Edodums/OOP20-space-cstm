package main.models.components.interfaces;

import main.models.components.entities.CommonShip;
import main.utils.Pair;
import java.util.Map;
import java.util.Optional;

import static main.models.Game.getMaxX;
import static main.models.Game.getMaxY;
import static main.models.components.entities.CommonShip.*;



public interface Entity {
    Map<Pair<Double, Double>, Optional<Entity>> create(Object newHashMap);
    Pair<Double, Double> getPosition();

    void fire();
    void die();

    double getSpawnNumber();

    default boolean canEntityMove(Double x, Double y) {
       return x < getMaxX() && y < getMaxY();
      }


    default boolean isNPC() {
        return this instanceof CommonShip;
    }

    default void move() {
        double unit = 1;
        double accelerationFactor = getAccelerationFactor();
        Pair<Double, Double> newPair = null;


        /* 1. if you've reach the end you set take away 1 life from player counter. */
        if (getPosition().getX().equals(getCommonEnemiesColumns()) &&
                getPosition().getY().equals(getCommonEnemiesRows() + getCommonEnemiesNextRows())) {
            //TODO: probably I'll handle it with an Event ( if so, @Arianna have to create an Event Manager )
            System.out.println("REACHING END");
            return;
        }

        /* 2. if you've to go right then check if the current number (x) is lower than the next one */
        if (!isComingRight(getPosition().getY())) {
            newPair = new Pair<>(getPosition().getX() + 1 + accelerationFactor, getPosition().getY());
        }

        /* 3. if you've to go left then check if the current number (x) is greater than the next one */
        if (isComingRight(getPosition().getY())) {
            newPair = new Pair<>(getPosition().getX() - 1 - accelerationFactor, getPosition().getY());
        }

        /* 4. if you reach the last column then go down and back (x - 1) && check if coming from the left */
        if (getPosition().getX().equals(getCommonEnemiesColumns()) && !isComingRight(getPosition().getY())) {
            newPair = new Pair<>(getPosition().getX() - 1 - accelerationFactor, getPosition().getY() + 1);
        }

        /* 5. if you reach the first column then go down and forth ( x + 1 ) && check if coming from the right */
        if (getPosition().getX().equals(getStartingPoint().getX()) && isComingRight(getPosition().getY())) {
            newPair = new Pair<>(getPosition().getX() + 1 + accelerationFactor, getPosition().getY() + 1);
        }

        if (newPair == null) {
            throw new NullPointerException("Not really an expected scenario");
        }

    }

    default double  getAccelerationFactor(){
        Pair<Double, Double> pair = null;

        return pair.getY() % getMaxX() * 0.25;
    }

    default boolean isComingRight(Double currentY) {
        return currentY % 2 == 0;
    }

}
