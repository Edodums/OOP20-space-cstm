package main.events;

import javafx.scene.image.ImageView;
import main.views.entities.PlayerShipView;

public class PlayerGoLeftEvent {
  private final ImageView imageView;
  private final double unit;
  
  public PlayerGoLeftEvent(PlayerShipView view, double unit) {
    this.imageView = view.get();
    this.unit = unit;
  }
  
  public void goLeft() {
    imageView.setTranslateX(imageView.getX() - (1 * unit));
  }
}
