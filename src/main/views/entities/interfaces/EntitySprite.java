package main.views.entities.interfaces;

import java.util.LinkedHashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import main.models.components.interfaces.Entity;
import main.models.settings.Grid;
import main.utils.Pair;


public interface EntitySprite {
    ImageView get();
    
    void set(ImageView imageView);
    
    default void add(Pane pane) {
        pane.getChildren().add(get());
    }
    
    default void create(Pair<Double, Double> position, Entity entity, double unit) {
        if (!isEntityDividedIntoGrid(entity)) {
            set(new ImageView(entity.getFilename()));
        } else {
            
            final Grid grid = entity.getTypeImages().getGrid();
            final Image[] images = getPartFromGrid(
                  entity.getFilename(),
                  grid.getSelectedRow(),
                  grid.getSelectedColumn(),
                  grid.getColumns(),
                  grid.getRows(),
                  grid.getInternalColumns()
            );
            
            if (!hasEntityInternalColumns(entity)) {
                set(new ImageView(images[0]));
            } else {
                final int index = (int) ((position.getY() - 1) % grid.getInternalColumns());
                set(new ImageView(images[index]));
            }
            
            update(position, unit);
        }
    }
    
    default void remove(Pane pane) {
        pane.getChildren().remove(get());
    }
    
    default void update(Pair<Double, Double> position,  double unit) {
        final ImageView  entity = get();
        
        entity.setTranslateX(position.getX() * unit);
        entity.setTranslateY(position.getY() * unit);
    }
    
    default boolean hasEntityInternalColumns(Entity entity) {
        final Grid grid  = entity.getTypeImages().getGrid();
        
        return grid.getInternalColumns() > 0;
    }
    
    default boolean isEntityDividedIntoGrid(Entity entity) {
        final Grid grid  = entity.getTypeImages().getGrid();
        
        return grid.getColumns() > 1  && grid.getRows() > 1;
    }
    
    default LinkedHashMap<Pair<Integer, Integer>, Image> getGridFromFile(String filename, int totalRows, int totalColumns) {
        final LinkedHashMap<Pair<Integer, Integer>, Image> grid = new LinkedHashMap<>();
        final Image image = new Image(filename);
        final PixelReader reader = image.getPixelReader();
        
        final int imageWidth = (int) Math.round(image.getWidth());
        final int imageHeight = (int) Math.round(image.getHeight());
        
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
            double width = Math.floor((double) gridImageWidth / (double) internalColumns);
        
            int x = (int) (gridPortionColumn * width);
        
            gridPortions[gridPortionColumn] = new WritableImage(reader, x, 0, (int) width, gridImageHeight);
        }
    
        return gridPortions;
    }
}
