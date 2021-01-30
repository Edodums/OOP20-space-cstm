package main.components;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;
import main.utils.ResourcePath;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Sprite {

    final Image getSingleFromFile(String filename) {
        return new Image(
            this.getClass().getResourceAsStream(
                ResourcePath.IMAGES_PATH.toString().concat(filename)
            )
        );
    }

    /*
    final Pair<Image, Image> getPairFromFile(String filename) {
        Image image = getSingleFromFile(filename);

        int imageWidth = (int) Math.round(image.getWidth());
        int imageHeight = (int) Math.round(image.getHeight());
        int halfImageWidth = imageWidth / 2;

        PixelReader reader = image.getPixelReader();

        WritableImage firstHalf = new WritableImage(reader,0, 0, halfImageWidth, imageHeight);
        WritableImage secondHalf = new WritableImage(reader, halfImageWidth, 0, halfImageWidth, imageHeight);

        return new Pair<>(firstHalf, secondHalf);
    }
    */

    final LinkedHashMap<Pair<Integer, Integer>, Image> getGridFromFile(String filename, int rows, int columns) {
        LinkedHashMap<Pair<Integer, Integer>, Image> grid = new LinkedHashMap<>();
        Image image = getSingleFromFile(filename);
        PixelReader reader = image.getPixelReader();

        final int imageWidth = (int) Math.round(image.getWidth());
        final int imageHeight = (int) Math.round(image.getHeight());

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                final int width = (imageWidth / columns);
                final int height = (imageHeight / rows);
                final int x = column * width;
                final int y = row * height;

                grid.put(new Pair<>(row, column), new WritableImage(reader, x, y, width, height));
            }
        }

        return grid;
    }

    final LinkedHashMap<Boolean, Image> getPartFromGrid(LinkedHashMap<Pair<Integer, Integer>, Image> grid, int gridRow, int gridColumn, int aliveRatio, int columns) {
        LinkedHashMap<Boolean, Image> gridPart = new LinkedHashMap<>();
        Pair<Integer, Integer> position = new Pair<>(gridRow, gridColumn);

        Image image = grid.get(position);
        PixelReader reader = image.getPixelReader();

        final int imageWidth = (int) Math.round(image.getWidth());
        final int imageHeight = (int) Math.round(image.getHeight());

        for (int column = 0; column < columns; column++) {
            final int width = (imageWidth / columns);
            final int x = column * width;
            // is alive if the image columns ratio is >= to the column taken in exam
            // Example: 6 sprites, 5/6 alive sprites, 1/6 dead sprite
            boolean isAlive = column < (aliveRatio - 1);

            gridPart.put(isAlive, new WritableImage(reader, x, 0, width, imageHeight));
        }

        return gridPart;
    }

    final ArrayList<Pair<Boolean, Image>> getPartFromGrid(String filename, int gridRow, int gridColumn, int aliveRatio, int totalColumns, int rows, int columns) {
        ArrayList<Pair<Boolean, Image>> gridPortion = new ArrayList<>();

        LinkedHashMap<Pair<Integer, Integer>, Image> grid = getGridFromFile(filename, rows, columns);
        Pair<Integer, Integer> position = new Pair<>(gridRow, gridColumn);

        Image gridImage = grid.get(position);
        PixelReader reader = gridImage.getPixelReader();

        final int gridImageWidth = (int) Math.round(gridImage.getWidth());
        final int gridImageHeight = (int) Math.round(gridImage.getHeight());

        final int gridPortions = totalColumns % aliveRatio + aliveRatio;
        final int imageParts = Math.max(totalColumns / aliveRatio, 2);

        for (int gridPortionColumn = 0; gridPortionColumn < gridPortions; gridPortionColumn++) {
            int width = (int) Math.floor(gridImageWidth / totalColumns);

            if (gridPortionColumn < gridPortions - 1) {
                width = width * imageParts;
            }

            int x = gridPortionColumn * width;

            // is alive if the gridImage gridPortionColumns ratio is >= to the gridPortionColumn taken in exam
            // Example: 6 sprites, 5/6 alive sprites, 1/6 dead sprite
            boolean isAlive = gridPortionColumn < (aliveRatio);

            gridPortion.add(new Pair<>(isAlive, new WritableImage(reader, x, 0, width, gridImageHeight)));
        }

        return gridPortion;
    }
}
