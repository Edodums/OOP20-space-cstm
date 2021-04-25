package main.events;

import javafx.scene.image.ImageView;
import main.views.entities.PlayerShipView;

public class PlayerGoRightEvent {
  private final ImageView imageView;
  private final double unit;
  
  public PlayerGoRightEvent(PlayerShipView view, double unit) {
    this.imageView = view.get();
    this.unit = unit;
  }
  
  public void goRight() {
    imageView.setTranslateX(imageView.getX() + (1 * unit));
  }
}
