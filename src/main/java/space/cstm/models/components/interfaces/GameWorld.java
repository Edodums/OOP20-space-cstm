package space.cstm.models.components.interfaces;

import space.cstm.utils.Pair;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface GameWorld extends WorldGrid {
    /**
     * Removes the given laser instance from the static field
     * @param weapon
     */
    void removeLaserInstance(Weapon weapon);

    /**
     * Creates and deploys the PlayerBeam
     * @return the instance of the weapon if the player is present
     */
    Weapon primaryFire();

    /**
     * Creates and deploys the EnemyBeam
     * @return the instance of the weapon if the enemy is present
     */
    Weapon enemyFire();

    /**
     * move of one position every laser
     */
    void moveLasers();

    /**
     * @param weapon
     * @return true if a given Weapon instance has collided with an Entity
     */
    boolean collisionHandler(Weapon weapon);

    /**
     * @return the stream function where the enemies instances and their position is
     */
    Stream<Map.Entry<Pair<Float, Float>, Optional<Entity>>> getEnemies();

    /**
     * @param gamePoints
     */
    void setGamePoints(float gamePoints);

    /**
     * @param aliveEnemies
     */
    void setAliveEnemies(float aliveEnemies);

    /**
     *
     * @return the player isntance
     */
    Optional<Entity> getPlayer();

    /**
     *
     * @return
     */
    String getPlayerName();

    /**
     *
     * @param playerName
     */
    void setPlayerName(String playerName);

    /**
     *
     * @return
     */
    Pair<Float, Float> getPlayerPosition();
}
