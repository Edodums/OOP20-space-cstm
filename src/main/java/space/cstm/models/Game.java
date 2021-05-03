package space.cstm.models;

import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import space.cstm.models.components.Collider;
import space.cstm.models.components.entities.EntityFactory;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.models.components.interfaces.GameWorld;
import space.cstm.models.components.interfaces.Weapon;
import space.cstm.models.components.weapons.EnemyBeam;
import space.cstm.models.components.weapons.PlayerBeam;
import space.cstm.models.components.weapons.WeaponFactory;
import space.cstm.models.settings.Settings;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.utils.Pair;
import space.cstm.utils.enums.EntityType;
import space.cstm.utils.enums.WeaponType;

/**
 *
 */
public class Game implements ObservableModel, GameWorld {
  private static final float MAX_X = 14;
  private static final float MAX_Y = 9;
  
  private static final float ENEMIES_COLUMNS = 8;
  private static final float ENEMIES_ROWS = 3;
  private static final float ENEMIES_NEXT_ROWS = 4;
  
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);
  private final WeaponFactory weaponFactory = new WeaponFactory();
  private final EntityFactory entityFactory = new EntityFactory();
  private final Map<Pair<Float, Float>, Optional<Entity>> grid = new HashMap<>();
  
  private Set<Entity> entities = new HashSet<>();
  private Set<Weapon> weapons = new HashSet<>();
  private Set<PlayerBeam> playerBeams = new HashSet<>();
  private Set<EnemyBeam> enemyBeams = new HashSet<>();
  
  private float gamePoints = 0.0f;
  private float aliveEnemies;
  private String playerName;
  
  /**
   *
   * @param settings
   */
  public Game(Settings settings) {
    settings.loadDefault();
    
    Collection<CustomizableTypeImage> typeImageStream =  settings.getTypeImages().values();
    
    initEntities(typeImageStream);
    initWeapons(typeImageStream);
  }
  
  private void initWeapons(Collection<CustomizableTypeImage> settings) {
    this.weapons = settings.stream()
                         .filter(typeImage -> !typeImage.getType().getClass().equals(EntityType.class))
                         .map(weaponFactory::getWeapon)
                         .collect(Collectors.toUnmodifiableSet());
  }
  
  /**
   *
   * @param settings
   */
  private void initEntities(Collection<CustomizableTypeImage> settings) {
    this.entities = settings.stream()
                          .filter(typeImage -> !typeImage.getType().getClass().equals(WeaponType.class))
                          .map(entityFactory::getEntity)
                          .collect(Collectors.toUnmodifiableSet());
  }
  
  /**
   *
   */
  @Override
  public void initGrid() {
    this.entities.forEach(entity -> entity.create().forEach(this.grid::put));
    fireGridChange(this.grid);
  }
  
  /**
   *
   */
  @Override
  public void updateGrid() {
    final Map<Pair<Float, Float>, Optional<Entity>> oldMap = new HashMap<>(getGrid());
    
    oldMap.forEach((pair, entity) -> {
      if (entity.isEmpty() || !entity.get().isNPC()) {
        return;
      }
      
      final Entity realEntity = entity.get();
      
      realEntity.move((Collider) realEntity, pair, getPlayer());
    });
    
    fireGridChange(oldMap);
  }
  
  /**
   *
   */
  private void fireGridChange(Map<Pair<Float, Float>, Optional<Entity>> old) {
    firePropertyChange("grid", old, this.grid);
  }

  @Override
  public void removeFromGrid(Entity entityToDelete) {
    this.grid.entrySet().removeIf(entry -> entry.getValue().isPresent() && entry.getValue().get().equals(entityToDelete));
    firePropertyChange("removeFromGrid", null, entityToDelete);
  }

  @Override
  public void removeLaserInstance(Weapon weapon) {
    if (weapon instanceof PlayerBeam) {
      this.playerBeams.remove(weapon);;
    }
  
    if (weapon instanceof EnemyBeam) {
      this.enemyBeams.remove(weapon);
    }
    
    firePropertyChange("removeWeapon", null, weapon);
  }
  
  /**
   *
   * @return
   */
  @Override
  public Weapon primaryFire() {
    if (getPlayer().isPresent())  {
      final PlayerBeam playerBeam = new PlayerBeam(getPlayerWeapon().getTypeImages());
      final Pair<Float, Float> beamInitialPosition = new Pair<>(getPlayerPosition().getX(), getPlayerPosition().getY() - 1);
      
      this.playerBeams.add(playerBeam);
      
      playerBeam.deploy(beamInitialPosition);
      
      return playerBeam;
    }
    
    return null;
  }

  @Override
  public Weapon enemyFire() {
    final Optional<Map.Entry<Pair<Float, Float>, Optional<Entity>>> enemyEntry = getEnemies().findAny();
    
    if (enemyEntry.isEmpty()) {
      return null;
    }
    
    final Optional<Entity> enemy = enemyEntry.get().getValue();
    final Pair<Float, Float> enemyPosition = enemyEntry.get().getKey();
    
    if (enemy.isPresent())  {
      final EnemyBeam enemyBeam = new EnemyBeam(getEnemyWeapon().getTypeImages());
      final Pair<Float, Float> beamInitialPosition = new Pair<>(enemyPosition.getX(), enemyPosition.getY() + 1);
      
      this.enemyBeams.add(enemyBeam);
      
      enemyBeam.deploy(beamInitialPosition);
      
      return enemyBeam;
    }
    
    return null;
  }

  @Override
  public void moveLasers() {
    this.playerBeams.stream().distinct().filter(Objects::nonNull).forEach(playerBeam -> {
      playerBeam.move();
      firePropertyChange("weapon", null, playerBeam);
    });
  
    this.enemyBeams.stream().distinct().filter(Objects::nonNull).forEach(enemyBeam -> {
      enemyBeam.move();
      firePropertyChange("weapon", null, enemyBeam);
    });
  }
  
  /**
   *
   * @return
   */
  @Override
  public boolean collisionHandler(Weapon weapon) {
    if (weapon instanceof PlayerBeam) {
      final Set<Entity> enemies = new HashSet<>(getEnemiesSetFromGrid());
      
      for (Entity entity : enemies)  {
        if (weapon.checkCollision((Collider) entity)) {
          removeFromGrid(entity); //  SIDE EFFECT: written to avoid duplication
          return true;
        }
      }
    }
    
    
    if (weapon instanceof EnemyBeam) {
      return getPlayer().isPresent() && weapon.checkCollision((Collider) getPlayer().get());
    }
    
    return false;
  }

  @Override
  public Stream<Map.Entry<Pair<Float, Float>, Optional<Entity>>> getEnemies() {
    return this.grid.entrySet()
                 .stream()
                 .filter(entry -> entry.getValue().isPresent())
                 .filter(entry -> entry.getValue().get().isNPC());
  }
  
  public Set<Entity> getEnemiesSetFromGrid() {
    final Set<Entity> enemies = new HashSet<>();
    
    for (Map.Entry<Pair<Float, Float>, Optional<Entity>> entry :  getGrid().entrySet()) {
        if (entry.getValue().isPresent() && entry.getValue().get().isNPC()) {
          enemies.add(entry.getValue().get());
        }
    }
    
    return enemies;
  }
  
  /**
   *
   * @return
   */
  @Override
  public Optional<Entity> getPlayer() {
    return this.entities.stream().filter(Entity::isPlayer).findFirst();
  }
  
  /**
   *
   * @return
   */
  private Weapon getPlayerWeapon() {
    return weapons.stream().filter(weapon -> weapon.getTypeImages().getType() != WeaponType.NPC).findFirst().get();
  }
  
  /**
   *
   * @return
   */
  private Weapon getEnemyWeapon() {
    return weapons.stream().filter(weapon -> weapon.getTypeImages().getType() == WeaponType.NPC).findFirst().get();
  }
  
  /**
   *
   * @return
   */
  @Override
  public Map<Pair<Float, Float>, Optional<Entity>> getGrid() {
    return this.grid;
  }
  
  /**
   *
   * @return
   */
  public static float getEnemiesColumns(){
    return ENEMIES_COLUMNS;
  }
  
  /**
   *
   * @return
   */
  public static float getEnemiesRows(){
    return ENEMIES_ROWS;
  }
  
  /**
   *
   * @return
   */
  public static float getEnemiesNextRows() {
    return ENEMIES_NEXT_ROWS;
  }
  
  /**
   *
   * @return
   */
  public static float getMaxX() {
    return MAX_X;
  }
  
  /**
   *
   * @return
   */
  public static float getMaxY() {
    return MAX_Y;
  }
  
  /**
   *
   * @return
   */
  public float getGamePoints() {
    return gamePoints;
  }
  
  /**
   *
   * @return
   */
  public float getAliveEnemies() {
    return aliveEnemies;
  }
  
  /**
   *
   * @return
   */
  @Override
  public Pair<Float, Float> getPlayerPosition() {
    if (getPlayer().isPresent()) {
      return ((Collider) getPlayer().get()).getPosition();
    }
    
    return new Pair<>(0f, 0f);
  }
  
  /**
   *
   * @return
   */
  @Override
  public PropertyChangeSupport getSupport() {
    return this.support;
  }
  
  /**
   *
   * @param playerName
   */
  @Override
  public void setPlayerName(String playerName) {
    String oldPlayerName = this.playerName;
    this.playerName = playerName;
    
    firePropertyChange("playerName", oldPlayerName, playerName);
  }
  
  /**
   *
   * @param gamePoints
   */
  @Override
  public void setGamePoints(float gamePoints) {
    float oldGamePoints = this.gamePoints;
    this.gamePoints = gamePoints;
    
    firePropertyChange("gamePoints", oldGamePoints, gamePoints);
  }
  
  /**
   *
   * @param aliveEnemies
   */
  @Override
  public void setAliveEnemies(float aliveEnemies) {
    float oldAliveEnemies = this.aliveEnemies;
    this.aliveEnemies = aliveEnemies;
    
    firePropertyChange("aliveEnemies", oldAliveEnemies, aliveEnemies);
  }
  
  public void createMotherShip() {
    // empty
  }

  @Override
  public String getPlayerName() {
    return this.playerName;
  }
}

