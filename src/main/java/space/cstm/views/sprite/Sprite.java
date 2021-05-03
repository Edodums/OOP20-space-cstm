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
  ImageView get();
  
  void set(ImageView imageView);
  
  default void add(Pane pane) {
    if (get().getImage().getWidth() > 400) {
      get().setFitWidth(24);
      get().setPreserveRatio(true);
    }
    
    pane.getChildren().add(get());
  }
  
  default void update(Pair<Float, Float> position, float unit) {
    final ImageView imageView = get();
  
    if (position != null && imageView != null) {
      imageView.setTranslateX(position.getX() * unit);
      imageView.setTranslateY(position.getY() * unit);
    }
  }
  
  default void remove(Pane pane) {
    if (get() != null) {
      pane.getChildren().remove(get());
      get().getImage().cancel();
    }
  }
  
  default void handleSpriteImage(GridImage grid, Pair<Float, Float> position, Image[] images, float unit) {
    if (!hasSpriteInternalColumns(grid)) {
      set(new ImageView(images[0]));
    } else {
      final int index = (int) ((position.getY() - 1) % grid.getInternalColumns());
      set(new ImageView(images[index]));
    }
    
    update(position, unit);
  }
  
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
  
  default LinkedHashMap<Pair<Integer, Integer>, Image> getGridFromFile(String filename, int totalRows, int totalColumns) {
    final LinkedHashMap<Pair<Integer, Integer>, Image> grid = new LinkedHashMap<>();
    final Image image = new Image(filename);
    final PixelReader reader = image.getPixelReader();
    
    final int imageWidth = (int) Math.round(image.getWidth());
    final int imageHeight = (int) Math.round(image.getHeight());
    
    if (totalRows == 1) {
      totalRows = 2;
    }
    
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
  
  default boolean hasSpriteInternalColumns(final GridImage grid) {
    return grid.getInternalColumns() > 0;
  }
  
  default boolean isSpriteDividedIntoGrid(final GridImage grid) {
    return grid.getColumns() <= 0 || grid.getRows() <= 0;
  }
}
