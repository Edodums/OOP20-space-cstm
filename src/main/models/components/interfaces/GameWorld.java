package main.models.components.interfaces;

import main.utils.Pair;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface GameWorld extends WorldGrid {
    void removeLaserInstance(Weapon weapon);

    Weapon primaryFire();

    Weapon enemyFire();

    void moveLasers();

    boolean collisionHandler(Weapon weapon);

    Stream<Map.Entry<Pair<Float, Float>, Optional<Entity>>> getEnemies();

    void setGamePoints(float gamePoints);

    void setAliveEnemies(float aliveEnemies);

    Optional<Entity> getPlayer();

    String getPlayerName();

    void setPlayerName(String playerName);

    Pair<Float, Float> getPlayerPosition();
}
