package main.views.entities.interfaces;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import main.utils.SpriteAnimation;

public interface Animated {
  default void animate(ImageView imageView) {
    /* count and columns params are not modeled that's why are passed as magic numbers */
    final double count = 2;
    final double columns = 2;
    
    final Animation animation = new SpriteAnimation(
          imageView,
          count,
          columns
    );
    
    animation.setCycleCount(Animation.INDEFINITE);
    animation.play();
  }
}
