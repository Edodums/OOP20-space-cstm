package main.models;

import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import main.models.components.Collider;
import main.models.components.entities.EntityFactory;
import main.models.components.interfaces.Entity;
import main.models.components.interfaces.Weapon;
import main.models.components.weapons.WeaponFactory;
import main.models.settings.Settings;
import main.utils.Pair;
import main.utils.enums.WeaponType;

/**
 *
 */
public class Game implements ObservableModel {
  private static final double MAX_X = 14;
  private static final double MAX_Y = 12;
  
  private static final double ENEMIES_COLUMNS = 8;
  private static final double ENEMIES_ROWS = 3;
  private static final double ENEMIES_NEXT_ROWS = 6;
  
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);
  private final WeaponFactory weaponFactory = new WeaponFactory();
  private final EntityFactory entityFactory = new EntityFactory();
  
  private Set<Entity> entities = new HashSet<>();
  private final Map<Pair<Double, Double>, Optional<Entity>> grid = new HashMap<>();
  
  private double gamePoints = 0.0;
  private double aliveEnemies;
  private String playerName;
  
  /**
   *
   * @param settings
   */
  public Game(Settings settings) {
    settings.loadDefault();
    
    initEntities(settings);
    initGrid();
    
    setAliveEnemies((double) this.grid
                                 .entrySet()
                                 .stream()
                                 .filter(entry -> entry.getValue().isPresent())
                                 .filter(entry -> entry.getValue().get().isNPC())
                                 .count());
  }
  
  /**
   *
   * @param settings
   */
  public void initEntities(Settings settings) {
    this.entities = settings
                          .getTypeImages()
                          .values()
                          .stream()
                          .filter(typeImage -> !typeImage.getType().getClass().equals(WeaponType.class))
                          .map(entityFactory::getEntity)
                          .collect(Collectors.toUnmodifiableSet());
  }
  
  /**
   *
   */
  public void updateGrid() {
    final Map<Pair<Double, Double>, Optional<Entity>> oldMap = new HashMap<>(getGrid());
    
    oldMap.forEach((pair, entity) -> {
      if (entity.isEmpty() || !entity.get().isNPC()) {
        return;
      }
      
      final Entity realEntity = entity.get();
      
      realEntity.move((Collider) realEntity, pair);
      
      this.grid.getOrDefault(realEntity, Optional.empty())
            .ifPresentOrElse(
                  _entity -> this.grid.replace(((Collider) realEntity).getPosition(), Optional.of(realEntity)),
                  () -> this.grid.put(((Collider) realEntity).getPosition(), Optional.of(realEntity))
            );
      
    });
  
    fireGridChange(oldMap);
  }
  
  /**
   *
   * @param position
   */
  public void removeFromGrid(Pair<Double, Double> position) {
    final Map<Pair<Double, Double>, Optional<Entity>> old = new HashMap<>(this.grid);
    
    if (this.grid.get(position).isPresent()) {
      this.grid.remove(position);
      fireGridChange(old);
    }
  
    System.out.println("Not present"); // TODO: handle it happens during alpha TESTS
  }
  
  /**
   *
   */
  public void primaryFire() {
    // TODO: change it with the current position of the player ( add a -1 to Y )
    getPlayerWeapon().deploy(new Pair<>(1.0, 1.0));
  }
  
  /**
   *
   */
  public void collisionHandler() {
    getSetOfNPCEntities()
          .stream()
          .filter(Optional::isPresent)
          .forEach(entity -> getPlayerWeapon().checkCollision((Collider) entity.get()));
  }
  
  /**
   *
   */
  private void initGrid() {
    IntStream.range(0, (int) getMaxX())
          .boxed()
          .flatMap(x -> IntStream.range(0, (int) getMaxY()).mapToObj(y -> new Pair<>((double) x, (double) y)))
          .forEachOrdered(pair -> this.grid.put(pair, Optional.empty()));
    
    final Map<Pair<Double, Double>, Optional<Entity>> old = new HashMap<>(this.grid);
    
    this.entities.forEach(entity -> {
      entity.create().forEach(this.grid::put);
    });
  
    
    fireGridChange(old);
  }
  
  /**
   *
   */
  private void fireGridChange(Map<Pair<Double, Double>, Optional<Entity>> old) {
    firePropertyChange("grid", old, this.grid);
  }
  
  /**
   *
   * @return
   */
  public Weapon getPlayerWeapon() {
    return weaponFactory.getWeapon(WeaponType.PLAYER);
  }
  
  /**
   *
   * @return
   */
  public Map<Pair<Double, Double>, Optional<Entity>> getGrid() {
    return this.grid;
  }
  
  /**
   *
   * @param entityFilter
   * @return
   */
  public Map<Pair<Double, Double>, Optional<Entity>> getFilteredEntitiesInGrid(Entity entityFilter) {
    return getGrid()
                 .entrySet()
                 .stream()
                 .filter(entry -> entry.getValue().isPresent())
                 .filter(entry -> Optional.of(entityFilter).equals(entry.getValue()))
                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
  
  /**
   *
   * @return
   */
  public Set<Optional<Entity>> getSetOfNPCEntities() {
    Set<Optional<Entity>> npcSet = new HashSet<>(Collections.emptySet());
    
    getEntitySet()
          .stream()
          .filter(Entity::isNPC)
          .forEach(entity -> npcSet.addAll(getFilteredEntitiesInGrid(entity).values()));
    
    return npcSet;
  }
  
  /**
   *
   * @return
   */
  public Set<Entity> getEntitySet() {
    return Collections.unmodifiableSet(this.entities);
  }
  
  /**
   *
   * @return
   */
  public static double getEnemiesColumns(){
    return ENEMIES_COLUMNS;
  }
  
  /**
   *
   * @return
   */
  public static double getEnemiesRows(){
    return ENEMIES_ROWS;
  }
  
  /**
   *
   * @return
   */
  public static double getEnemiesNextRows() {
    return ENEMIES_NEXT_ROWS;
  }
  
  /**
   *
   * @return
   */
  public static double getMaxX() {
    return MAX_X;
  }
  
  /**
   *
   * @return
   */
  public static double getMaxY() {
    return MAX_Y;
  }
  
  /**
   *
   * @return
   */
  public double getGamePoints() {
    return gamePoints;
  }
  
  /**
   *
   * @return
   */
  public double getAliveEnemies() {
    return aliveEnemies;
  }
  
  /**
   *
   * @param playerName
   */
  public void setPlayerName(String playerName) {
    String oldPlayerName = this.playerName;
    this.playerName = playerName;
    
    firePropertyChange("playerName", oldPlayerName, playerName);
  }
  
  /**
   *
   * @param gamePoints
   */
  public void setGamePoints(double gamePoints) {
    double oldGamePoints = this.gamePoints;
    this.gamePoints = gamePoints;
    
    firePropertyChange("gamePoints", oldGamePoints, gamePoints);
  }
  
  /**
   *
   * @param aliveEnemies
   */
  public void setAliveEnemies(double aliveEnemies) {
    double oldAliveEnemies = this.aliveEnemies;
    this.aliveEnemies = aliveEnemies;
    
    firePropertyChange("aliveEnemies", oldAliveEnemies, aliveEnemies);
  }
  
  /**
   *
   * @return
   */
  @Override
  public PropertyChangeSupport getSupport() {
    return this.support;
  }
}

