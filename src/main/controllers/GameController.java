package main.controllers;

import main.models.Game;
import main.models.ObservableModel;

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
    this.model.setPlayerName(playerName);
  }
  
  public void updateGrid() {
    this.model.updateGrid();
  }
}
