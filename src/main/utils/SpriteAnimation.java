package main.utils;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Utility Class that handles how the animation of a imageView works.
 * Taken from here: https://netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
 */
public class SpriteAnimation extends Transition {
  private static final Duration DURATION = new Duration(400.0);

  private final ImageView imageView;
  private final double count;
  private final double columns;
  private final double width;
  private final double height;

  private double lastIndex;

  /**
    * Pass the imageView, set duration of animation and a linear interpolator
    *  @param imageView
    *  @param count
    *  @param columns
   *
  */
  public SpriteAnimation(ImageView imageView, double count, double columns) {
    this.imageView = imageView;
    this.count = count;
    this.columns = columns;
    this.width = imageView.getImage().getWidth();
    this.height = imageView.getImage().getHeight();

    setCycleDuration(DURATION);
    setInterpolator(Interpolator.LINEAR);
  }

  protected void interpolate(double k) {
    final double index = Math.floor(k * count);

    if (index != lastIndex) {
      final double x = index % columns * (width / columns);
      imageView.setViewport(new Rectangle2D(x, 0, width / columns, height));

      lastIndex = index;
    }
  }
}
