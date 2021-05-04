package space.cstm.views.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.models.settings.interfaces.GridImage;
import space.cstm.utils.Pair;

import java.util.LinkedHashMap;

public interface Sprite {
  /**
   *
   * @return the ImageView field that concrete classes that implement WeaponSprite / EntitySprite have
   */
  ImageView get();

  /**
   *
   * @param imageView to set in the concrete class unique field
   */
  void set(ImageView imageView);

  /**
   *
   * @param pane add the ImageView to parent pane
   */
  default void add(Pane pane) {
    pane.getChildren().add(get());
  }

  /**
   * Updated current position of the imageview applying a translation in the X/Y axis
   * @param position current position
   * @param unit that is defined in GameView
   */
  default void update(Pair<Float, Float> position, float unit) {
    final ImageView imageView = get();
  
    if (position != null && imageView != null) {
      imageView.setTranslateX(position.getX() * unit);
      imageView.setTranslateY(position.getY() * unit);
    }
  }

  /**
   *
   * @param pane from the parent pane the ImageView, cancelling also the Image property
   */
  default void remove(Pane pane) {
    if (get() != null) {
      pane.getChildren().remove(get());
      get().getImage().cancel();
    }
  }

  /**
   *
   * @param grid defines how the image submitted in the setting, correlated to an Enity/Weapon is divided
   * @param position current position
   * @param images all the sub-images that are contained in a given image
   * @param unit that is defined in GameView
   */
  default void handleSpriteImage(GridImage grid, Pair<Float, Float> position, Image[] images, float unit) {
    if (!hasSpriteInternalColumns(grid)) {
      set(new ImageView(images[0]));
    } else {
      final int index = (int) ((position.getY() - 1) % grid.getInternalColumns());
      set(new ImageView(images[index]));
    }
    
    update(position, unit);
  }

  /**
   *
   * @param typeImage gives filename, grid and type
   * @return gets all the sub-images contained in the given image
   */
  default Image[] getImages(CustomizableTypeImage typeImage) {
    final GridImage grid = typeImage.getGrid();
    
    return getPartFromGrid(
          typeImage.getName(),
          grid.getSelectedRow(),
          grid.getSelectedColumn(),
          grid.getColumns(),
          grid.getRows(),
          grid.getInternalColumns()
    );
  }

  /**
   *
   * @param filename of the image, is the absolute path when the source is external
   * @param totalRows of the grid
   * @param totalColumns of the grid
   * @return a LinkedHashMap with the position of the sub-image in the given image and the image itself
   */
  default LinkedHashMap<Pair<Integer, Integer>, Image> getGridFromFile(String filename, int totalRows, int totalColumns) {
    final LinkedHashMap<Pair<Integer, Integer>, Image> grid = new LinkedHashMap<>();
    final Image image = new Image(filename);
    final PixelReader reader = image.getPixelReader();
    
    final int imageWidth = (int) Math.round(image.getWidth());
    final int imageHeight = (int) Math.round(image.getHeight());

    /* The only ugly solution I've come up to fix the nested for bug */
    if (totalRows == 1) {
      totalRows = 2;
    }

    /* The Images didn't get the right ratio if the I didn't this */
    if (totalColumns == 1) {
      totalColumns = 2;
    }
    
    for (int row = 0; row < totalRows; row++) {
      for (int column = 0; column < totalColumns; column++) {
        final int width = (imageWidth / totalColumns);
        final int height = (imageHeight / totalRows);
        final int x = column * width;
        final int y = row * height;
        
        grid.put(new Pair<>(row, column), new WritableImage(reader, x, y, width, height));
      }
    }
    
    return grid;
  }

  /**
   *
   * @param filename of the image
   * @param selectedRow of the grid
   * @param selectedColumn of the grid
   * @param totalColumns of the grid
   * @param totalRows of the grid
   * @param internalColumns of the grid
   * @return all images contained in the selected part of a grid
   */
  default Image[] getPartFromGrid(String filename, int selectedRow, int selectedColumn, int totalColumns, int totalRows, int internalColumns) {
    final int portions = internalColumns > 0 ? internalColumns : 1;
    final Image[] gridPortions = new Image[portions];
    
    final LinkedHashMap<Pair<Integer, Integer>, Image> grid = getGridFromFile(filename, totalRows, totalColumns);
    final Pair<Integer, Integer> position = new Pair<>(selectedRow, selectedColumn);
    
    final Image gridImage = grid.get(position);
    
    if (internalColumns == 0) {
      gridPortions[0] = gridImage;
      return gridPortions;
    }
    
    final PixelReader reader = gridImage.getPixelReader();
    
    final int gridImageWidth = (int) Math.round(gridImage.getWidth());
    final int gridImageHeight = (int) Math.round(gridImage.getHeight());
    
    for (int gridPortionColumn = 0; gridPortionColumn < internalColumns; gridPortionColumn++) {
      float width = (float) Math.floor((float) gridImageWidth / (float) internalColumns);
      
      int x = (int) (gridPortionColumn * width);
      
      gridPortions[gridPortionColumn] = new WritableImage(reader, x, 0, (int) width, gridImageHeight);
    }
    
    return gridPortions;
  }

  /**
   *
   * @param grid
   * @return true if a column of the grid is divided itself into sub-columns
   */
  default boolean hasSpriteInternalColumns(final GridImage grid) {
    return grid.getInternalColumns() > 0;
  }

  /**
   *
   * @param grid
   * @return true if the image is divided into a grid
   */
  default boolean isSpriteDividedIntoGrid(final GridImage grid) {
    return grid.getColumns() <= 0 || grid.getRows() <= 0;
  }
}
