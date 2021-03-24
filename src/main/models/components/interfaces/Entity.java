package main.models.components.interfaces;

import main.utils.Pair;

import java.util.Map;
import java.util.Optional;

import static main.models.Game.getMaxX;
import static main.models.Game.getMaxY;

public interface Entity {
    Map<Pair<Integer, Integer>, Optional<Entity>> create();
    void move(double velocity);
    void shoot();
    void die();
    Integer getSpawnNumber();

    default boolean canEntityMove(Integer x, Integer y) {
        return x < getMaxX() && y < getMaxY();
    }
}
