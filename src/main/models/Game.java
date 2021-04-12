package main.models;

import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import main.exceptions.SettingsNotLoaded;
import main.models.components.Collider;
import main.models.components.entities.EntityFactory;
import main.models.components.interfaces.Entity;
import main.models.components.interfaces.Weapon;
import main.models.components.weapons.WeaponFactory;
import main.utils.Pair;
import main.utils.enums.WeaponType;

public class Game extends ObservableModel {
  private static final double MAX_X = 14;
  private static final double MAX_Y = 12;
  
  private static final double ENEMIES_COLUMNS = 8;
  private static final double ENEMIES_ROWS = 3;
  private static final double ENEMIES_NEXT_ROWS = 6;

  private final WeaponFactory weaponFactory = new WeaponFactory();
  private final EntityFactory entityFactory = new EntityFactory();
  
  private Set<Entity> entities = new HashSet<>();
  private final Map<Pair<Double, Double>, Optional<Entity>> grid = new HashMap<>();
  
  private double gamePoints = 0.0;
  private double aliveEnemies;
  private String playerName;
  
  public Game() throws SettingsNotLoaded {
    if (getEntitySet().isEmpty()) {
      throw new SettingsNotLoaded();
    }
  }
  
  public Game(Settings settings) {
    // TODO: CHECK IF MODEL GETS CALLED BEFORE VIEW
    setSupport(new PropertyChangeSupport(this));
    
    initEntities(settings);
    initGrid();
    
    setAliveEnemies((double) this.grid
                                 .entrySet()
                                 .stream()
                                 .filter(entry -> entry.getValue().isPresent())
                                 .filter(entry -> entry.getValue().get().isNPC())
                                 .count());
  }
  
  public void initEntities(Settings settings) {
    this.entities = settings.getEntityImage()
                          .stream()
                          .map(entityFactory::getEntity)
                          .collect(Collectors.toUnmodifiableSet());
  }
  
  public void updateGrid() {
    for (Entity entity : this.entities) {
      if (entity.isNPC()) {
        entity.move((Collider) entity);
        this.grid.replace(entity.getPosition(), Optional.of(entity));
      }
    }
    
    fireGridChange();
  }
  
  public void removeFromGrid(Pair<Double, Double> position) {
    if (this.grid.get(position).isPresent()) {
      this.grid.remove(position);
      fireGridChange();
    }
  
    System.out.println("Not present"); // TODO: handle it happens during alpha TESTS
  }
  
  public void primaryFire() {
    // TODO: change it with the current position of the player ( add a -1 to Y )
    getPlayerWeapon().deploy(new Pair<>(1.0, 1.0));
  }
  
  public void collisionHandler() {
    getSetOfNPCEntities()
          .stream()
          .filter(Optional::isPresent)
          .forEach((entity) -> {
            getPlayerWeapon().checkCollision((Collider) entity.get());
          });
  }
  
  private void initGrid() {
    IntStream.range(0, (int) getMaxX())
          .boxed()
          .flatMap(x -> IntStream.range(0, (int) getMaxY()).mapToObj(y -> new Pair<>(x, y)))
          .forEach(pair -> this.grid.put(pair, Optional.empty()));
    
    this.entities.forEach(entity -> entity.create().forEach(this.grid::replace));
    
    fireGridChange();
  }
  
  private void fireGridChange() {
    firePropertyChange("grid", this.grid, this.grid);
  }
  
  public Weapon getPlayerWeapon() {
    return weaponFactory.getWeapon(WeaponType.PLAYER);
  }
  
  public Map<Pair<Double, Double>, Optional<Entity>> getGrid() {
    return grid;
  }
  
  public Map<Pair<Double, Double>, Optional<Entity>> getFilteredEntitiesInGrid(Entity entityFilter) {
    return getGrid()
                 .entrySet()
                 .stream()
                 .filter(entry -> Optional.of(entityFilter).equals(entry.getValue()))
                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
  
  public Set<Optional<Entity>> getSetOfNPCEntities() {
    Set<Optional<Entity>> npcSet = new HashSet<>(Collections.emptySet());
    
    getEntitySet()
          .stream()
          .filter(Entity::isNPC)
          .forEach(entity -> {
            npcSet.addAll(getFilteredEntitiesInGrid(entity).values());
          });
    
    return npcSet;
  }
  
  public Set<Entity> getEntitySet() {
    return Collections.unmodifiableSet(this.entities);
  }
  
  public static double getEnemiesColumns(){
    return ENEMIES_COLUMNS;
  }
  
  public static double getEnemiesRows(){
    return ENEMIES_ROWS;
  }
  
  public static double getEnemiesNextRows() {
    return ENEMIES_NEXT_ROWS;
  }
  
  public static double getMaxX() {
    return MAX_X;
  }
  
  public static double getMaxY() {
    return MAX_Y;
  }
  
  public double getGamePoints() {
    return gamePoints;
  }
  
  public double getAliveEnemies() {
    return aliveEnemies;
  }
  
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
    firePropertyChange("playerName", this.playerName, playerName);
  }
  
  public void setGamePoints(double gamePoints) {
    this.gamePoints = gamePoints;
    firePropertyChange("gamePoints", this.gamePoints, gamePoints);
  }
  
  public void setAliveEnemies(double aliveEnemies) {
    this.aliveEnemies = aliveEnemies;
    firePropertyChange("aliveEnemies", this.aliveEnemies, aliveEnemies);
  }
}

