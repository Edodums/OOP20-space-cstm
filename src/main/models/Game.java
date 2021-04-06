package main.models;

import main.exceptions.SettingsNotLoaded;
import main.models.components.EntityFactory;
import main.models.components.interfaces.Entity;
import main.utils.Pair;

import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game extends ObservableModel {
    private static final Integer MAX_X = 14;
    private static final Integer MAX_Y = 12;
    private static final double ENEMIES_COLUMNS = 8;
    private static final double ENEMIES_ROWS = 3;

    private final EntityFactory entityFactory = new EntityFactory();

    private Set<Entity> entities = new HashSet<>();
    private Map<Pair<Integer, Integer>, Optional<Entity>> grid = new HashMap<>();

    private Integer gamePoints = 0;
    private Integer aliveEnemies;
    private String playerName;

    public Game() throws SettingsNotLoaded {
        if (getEntitySet().isEmpty()) throw new SettingsNotLoaded();
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
                                .map(entityImage -> entityFactory.getEntity(entityImage.getEntityType()))
                                .collect(Collectors.toUnmodifiableSet());
    }

    public void initGrid() {
        IntStream.range(0, getMaxX())
                .boxed()
                .flatMap(x -> IntStream.range(0, getMaxY())
                        .mapToObj(y -> new Pair<>(x, y)))
                .forEach(pair -> this.grid.put(pair, Optional.empty()));

        this.entities.forEach(entity ->
            entity.create(newHashMap).forEach((key , value) -> this.grid.replace(key, value))
        );
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
        firePropertyChange("playerName", this.playerName, playerName);
        this.playerName = playerName;
    }

    public void setGamePoints(Integer gamePoints) {
        firePropertyChange("gamePoints", this.gamePoints, gamePoints);
        this.gamePoints = gamePoints;
    }

    public void setAliveEnemies(Integer aliveEnemies) {
        firePropertyChange("aliveEnemies", this.aliveEnemies, aliveEnemies);
        this.aliveEnemies = aliveEnemies;
    }

    public static double getEnemiesColumns(){
        return ENEMIES_COLUMNS;
    }

    public static double getEnemiesRows(){
        return ENEMIES_ROWS;
    }
}

