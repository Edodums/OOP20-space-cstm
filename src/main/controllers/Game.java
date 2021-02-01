package main.controllers;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.components.CommonShip;
import main.components.MotherShip;
import main.exceptions.SettingsNotFound;
import main.exceptions.UnknownSetting;
import main.utils.Fxml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class Game extends Controller {
    private final HashMap<String, String> settings = null;

    /*
    * By default, the FXMLLoader will create the controller
    * by calling its zero-argument constructor.
    * As stated here: https://stackoverflow.com/a/28845702/13455322
    */
    public Game() {}

    public Game(Stage stage) {
        String filename = Fxml.GAME.toString();

        setParent(filename);
        setScene();
        addPlayer();
        addEnemies();

        // initSettings();

        stage.setScene(getScene());
        stage.show();
    }

    private void addPlayer() {
        /*this.parent.getChildren()
                   .add(new Rectangle(225, 225, Color.BLUE));*/

    }

    private void addEnemies() {
        final GridPane parent = (GridPane) getParent();
        final CommonShip commonShip = new CommonShip();
        final List<ImageView> enemies = commonShip.getCommonShips();
        final Group group = new Group();
        final MotherShip motherShip = new MotherShip();
        final ImageView motherOfEnemies = motherShip.getMotherShip();

        group.getChildren().addAll(enemies);

        final Bounds bounds = group.getLayoutBounds();
        System.out.println("bounds:" + bounds);
        // Centering the group by get the difference between him and his father component
        group.setTranslateX(parent.getWidth() / 2 - bounds.getWidth() / 2);
        commonShip.move(group, 3.0);
        motherShip.move(motherOfEnemies, 3.0);
        parent.getChildren().add(group);
        parent.getChildren().add(motherOfEnemies);

    }

    private void initSettings() {
        // ask SettingService to load data and return it as an HashMap<String,String> ( name, setting to apply

        if (this.settings != null) {
            throw new SettingsNotFound("Check the settings.yaml file");
        }

        this.settings.forEach((key, value) -> {
            switch (key) {
                case "beam":
                    initLaserBeam();
                    break;
                case "player":
                    initPlayer();
                case "enemies":
                    initEnemies();
                case "orientation":
                    intiOrientation();

                default: throw new UnknownSetting("Check the Setting table, maybe there's typo");
            }
        });
    }

    private static void initLaserBeam() {
        // TODO: make all the LaserBeam classes ( Sprite, LaserBeam, PlayerBeam, EnemyBeam )
    }

    private static void initPlayer() {
        // TODO: make all the Player class ( Entity, Player )
    }

    private static void initEnemies() {
        // TODO: make all the Enemies class ( Entity, EnemyShip, CommonShip, MotherShip )
    }

    private static void intiOrientation() {
        // TODO: think about it with @arianna
    }
}
