package main.models.components.interfaces;

import java.util.Map;
import java.util.Optional;
import main.models.components.entities.CommonShip;
import main.utils.Pair;

public interface Entity {
  Map<Pair<Integer, Integer>, Optional<Entity>> create();

  Pair<Integer, Integer> getPosition();

  void move();

  void fire();

  void die();

  String getFilename();

  String getSpriteType();

  Integer getSpawnNumber();

  /**
   * TODO: check it and comment about it,if you think this is method more suitable for Player
   * default boolean canEntityMove(Integer x, Integer y) {
   *   return x < getMaxX() && y < getMaxY();
   * }
   */

  default boolean isNpc() {
    return this instanceof CommonShip;
  }
}
