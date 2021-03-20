package main.models;

import main.components.interfaces.Entity;
import main.utils.Pair;

import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Game extends ObservableModel {
    private static final Integer MAX_X_POSITION = 4;
    private static final Integer MAX_Y_POSITION = 12;

    private Map<Pair<Integer,Integer>, Entity> entities = new HashMap<>();
    private Integer gamePoints = 0;
    private Integer aliveEnemies;
    private String playerName;

    public Map<Pair<Integer, Integer>, Entity> entitiesPosition() {  return Collections.unmodifiableMap(this.entities); }

    public Game() {
        setSupport(new PropertyChangeSupport(this));
    }

    public void initEntities(Settings settings) {
        // TODO: init entities getting serialized data from settings
    }

    public boolean canEntityMove(Integer x, Integer y) {
        return x < MAX_X_POSITION && y < MAX_Y_POSITION;
    }

    public void moveEntity(Entity entity) {
        /* TODO:
         Map<Pair<Integer, Integer>, Entity> currentEntitiesPosition = entitiesPosition();
         always change position to the left
         when you reach y max position go 1 down
         when you reach x max position is finished*/
    }

    public void primaryFire() {
        //  TODO: handle position & spawn of entity laser
    }

    private void initCommonShip() {
        /* TODO:
         from index 1 to size -1 ( or minus 2 )
         create a CommonShip Entity
         for x columns and y rows
         set also the position x,y
         in this case I think It would be needed a Component or a the Factory Method Pattern*/
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
}
