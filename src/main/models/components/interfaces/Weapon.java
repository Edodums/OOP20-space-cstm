package main.models.components.interfaces;

import main.models.components.Collider;
import main.utils.Pair;

public interface Weapon {
    void deploy(Pair<Integer, Integer> startingPoint);
    void checkCollision(Collider entityToCheck);
}
