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
import main.models.components.entities.EntityFactory;
import main.models.components.interfaces.Entity;
import main.models.components.interfaces.Weapon;
import main.models.components.weapons.WeaponFactory;
import main.utils.Pair;
import main.utils.enums.WeaponType;

public class Game extends ObservableModel {
  private static final Integer MAX_X = 14;
  private static final Integer MAX_Y = 12;
  
  private final WeaponFactory weaponFactory = new WeaponFactory();
  private final EntityFactory entityFactory = new EntityFactory();
  
  private Set<Entity> entities = new HashSet<>();
  private final Map<Pair<Integer, Integer>, Optional<Entity>> grid = new HashMap<>();
  
  private Integer gamePoints = 0;
  private Integer aliveEnemies;
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
  }
  
  public void initEntities(Settings settings) {
    this.entities = settings.getEntityImage()
                          .stream()
                          .map(entityImage -> entityFactory.getEntity(entityImage))
                          .collect(Collectors.toUnmodifiableSet());
  }
  
  public void updateGrid() {
    for (Entity entity : this.entities) {
      if (entity.isNpc()) {
        entity.move();
        this.grid.replace(entity.getPosition(), Optional.of(entity));
      }
    }
    
    fireGridChange();
  }
  
  public void primaryFire() {
    WeaponType playerWeaponType = WeaponType.PLAYER;
    Weapon weapon = weaponFactory.getWeapon(playerWeaponType);
    // TODO: startingPoint is the current position of the player
    weapon.deploy(new Pair<>(1, 1));
  }
  
  public Map<Pair<Integer, Integer>, Optional<Entity>> getGrid() {
    return grid;
  }
  
  public Map<Pair<Integer, Integer>, Optional<Entity>> getFilteredEntitiesInGrid(Entity entityFilter) {
    return getGrid()
                 .entrySet()
                 .stream()
                 .filter(entry -> Optional.of(entityFilter).equals(entry.getValue()))
                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
  
  public Set<Entity> getEntitySet() {
    return Collections.unmodifiableSet(this.entities);
  }
  
  public static Integer getMaxX() {
    return MAX_X;
  }
  
  public static Integer getMaxY() {
    return MAX_Y;
  }
  
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
    firePropertyChange("playerName", this.playerName, playerName);
  }
  
  public void setGamePoints(Integer gamePoints) {
    this.gamePoints = gamePoints;
    firePropertyChange("gamePoints", this.gamePoints, gamePoints);
  }
  
  public void setAliveEnemies(Integer aliveEnemies) {
    this.aliveEnemies = aliveEnemies;
    firePropertyChange("aliveEnemies", this.aliveEnemies, aliveEnemies);
  }
  
  private void initGrid() {
    IntStream.range(0, getMaxX())
          .boxed()
          .flatMap(x -> IntStream.range(0, getMaxY())
                              .mapToObj(y -> new Pair<>(x, y)))
          .forEach(pair -> this.grid.put(pair, Optional.empty()));
    
    this.entities.forEach(entity ->
                                entity.create().forEach(this.grid::replace)
    );
    
    fireGridChange();
  }
  
  private void fireGridChange() {
    firePropertyChange("grid", this.grid, this.grid);
  }
}
