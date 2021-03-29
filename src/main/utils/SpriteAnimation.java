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
  private static final Integer OFFSET_X = 6;
  private static final Integer OFFSET_Y = 4;
  private static final Duration DURATION = new Duration(2.0);

  private final ImageView imageView;
  private final int count;
  private final int columns;
  private final int rows;
  private final int width;
  private final int height;

  private int lastIndex;

  /**
    * Pass imageView and it's data, probably will use reflection.
    *
    * @param imageView
    *
    * @param count
    *
    * @param columns
    *
    * @param rows
    *
    * @param width
    *
    * @param height
   *
  */
  public SpriteAnimation(
       ImageView imageView,
        int count,
        int columns,
        int rows,
        int width,
        int height
  ) {
    this.imageView = imageView;
    this.count     = count;
    this.columns   = columns;
    this.rows   = rows;
    this.width     = width;
    this.height    = height;

    setCycleDuration(DURATION);
    setInterpolator(Interpolator.LINEAR);
  }

  protected void interpolate(double k) {
    final int index = Math.min((int) Math.floor(k * count), count - 1);

    if (index != lastIndex) {
      final int columnIndex = index % columns;
      final int rowIndex = index % rows;
      final int x =  columnIndex * (width  + OFFSET_X);
      final int y = rowIndex * height + OFFSET_Y;

      imageView.setViewport(new Rectangle2D(x, y, width, height));

      lastIndex = index;
    }
  }
}
