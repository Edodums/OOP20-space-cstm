package main.controllers;

import main.models.Game;
import main.models.ObservableModel;
import main.models.components.interfaces.Entity;
import main.models.components.interfaces.Weapon;
import main.utils.Pair;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class GameController implements Controller {
  private Game model;
  
  public GameController(final Game game) {
    setModel(game);
  }
  
  @Override
  public void setModel(ObservableModel model) {
    this.model = (Game) model;
  }
  
  public Game getModel() {
    return this.model;
  }
  
  public void setPlayerName(String playerName) {
    getModel().setPlayerName(playerName);
  }
  
  public void updateGrid() {
    getModel().updateGrid();
  }
  
  public Pair<Float, Float> getPlayerPosition() {
    return getModel().getPlayerPosition();
  }
  
  public void moveLasers() {
    getModel().moveLasers();
  }
  
  public void removeLaserInstance(Weapon weapon) {
    getModel().removeLaserInstance(weapon);
  }
  
  public boolean collisionHandler(Weapon weapon) {
    return getModel().collisionHandler(weapon);
  }
  
  public void initGrid() {
    getModel().initGrid();
  }
  
  public Stream<Map.Entry<Pair<Float, Float>, Optional<Entity>>> getEnemies() {
    return getModel().getEnemies();
  }
  
  public void setAliveEnemies() {
    getModel().setAliveEnemies((float) getEnemies().count());
  }
  
  public Weapon primaryFire() {
    return getModel().primaryFire();
  }
  
  public Weapon enemyFire() {
    Random random = new Random();
    
    if (random.nextBoolean()) {
      return getModel().enemyFire();
    }
    
    return null;
  }
  
  public void createMotherShip() {
    getModel().createMotherShip();
  }
  
  public float getGamePoints() {
    return getModel().getGamePoints();
  }
  
  public String getPlayerName() {
    return getModel().getPlayerName();
  }
}
