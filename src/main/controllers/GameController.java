package main.controllers;

import main.models.Game;
import main.models.ObservableModel;
import main.models.Settings;

public class GameController implements Controller {
  private Game model;
  
  public GameController() {
    Settings settings = new Settings();
    setModel(new Game(settings.load()));
  }
  
  @Override
  public void setModel(ObservableModel model) {
    this.model = (Game) model;
  }
  
  public void setPlayerName(String playerName) {
    this.model.setPlayerName(playerName);
  }
  
  public void updateGrid() {
    this.model.updateGrid();
  }
}
