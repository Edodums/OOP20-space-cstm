package main.components;

import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.Pair;
import main.components.interfaces.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CommonShip extends Enemy {
    private static final int COLUMNS  =  2;
    private static final int COUNT    =  2;
    private static final int OFFSET_X =  4;
    private static final int OFFSET_Y =  0;

    @Override
    public void move(Node node, double velocity) {
        final Group group  = (Group) node;
        final Bounds bounds = group.getLayoutBounds();
        final Duration duration = Duration.seconds(velocity);

        TranslateTransition[] transitions = new TranslateTransition[7];

        for (int i = 0; i <= 6; i++) {
            final TranslateTransition transition = new TranslateTransition();

            if (i % 4 == 0) {
                transition.setByX(100);
            }

            if (i % 4 == 1 || i % 4 == 3) {
                transition.setByY(100);
            }

            if (i % 4 == 2) {
                transition.setByX(-100);
            }

            transitions[i] = transition;
        }

        SequentialTransition transitionSequence = new SequentialTransition(transitions);

        transitionSequence.setNode(group);
        transitionSequence.setCycleCount(Animation.INDEFINITE);
        transitionSequence.play();
    }

    @Override
    public void shoot() {

    }

    @Override
    public void die() {

    }

    @Override
    public void looseLife() {

    }

    @Override
    public void animate(ImageView imageView, int width, int height) {
        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT,
                COLUMNS,
                OFFSET_X,
                OFFSET_Y,
                width,
                height
        );

        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    public List<ImageView> getCommonShips() {
        List<ImageView> commonShips = new ArrayList<>();

        ArrayList<Pair<Boolean, Image>> gridPart = getPartFromGrid("space_invaders_sprite.png",5,1,3,7,6,2);

        int aliveImages = (int) gridPart.stream()
                                        .filter(Pair::getKey)
                                        .count();

        IntStream.range(0, aliveImages)
                .forEachOrdered(index -> {
                    final Pair<Boolean, Image> pair = gridPart.get(index);
                    final Image image = pair.getValue();
                    final int imageWidth = (int) Math.round(image.getWidth());
                    final int imageHeight = (int) Math.round(image.getHeight());

                    IntStream.rangeClosed(1, 10)
                            .forEachOrdered((spawnIndex) -> {
                                final ImageView enemy = new ImageView(image);
                                final double translateX = (imageWidth * spawnIndex) * 0.65;
                                final double translateY = (imageHeight * index) * 0.85;

                                this.animate(enemy, imageWidth / 2, imageHeight);

                                enemy.setTranslateX(translateX);
                                enemy.setTranslateY(translateY);

                                commonShips.add(enemy);
                            });
                });

        return commonShips;
    }

    public CommonShip commonShipBuilder() {
        return null;
    }

    @Override
    public boolean wasHit(Entity entityOne, Entity entityTwo) {
        return false;
    }
}
