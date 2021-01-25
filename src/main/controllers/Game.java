package main.controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.exceptions.SettingsNotFound;
import main.exceptions.UnknownSetting;
import main.utils.Fxml;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Game {
    private final HashMap<String, String> settings = null;
    private Scene scene;
    private GridPane parent;

   /*
    * By default, the FXMLLoader will create the controller
    * by calling its zero-argument constructor.
    * As stated here: https://stackoverflow.com/a/28845702/13455322
    */
    public Game() {}

    public Game(Stage stage) {
            setParent();
            setScene(this.parent);
            addPlayer();
            addEnemies();

            // initSettings();

            stage.setScene(this.scene);
            stage.show();

    }

    private void addPlayer() {
        /*this.parent.getChildren()
                   .add(new Rectangle(225, 225, Color.BLUE));*/

    }

    private void addEnemies() {
        List<Rectangle> enemies = new java.util.ArrayList<>();

        /*
        for (int i = 0; i < 15; i++) {
            final Rectangle enemy = new Rectangle(100, 100, Color.RED);
            enemy.setTranslateX(110 * i);
            enemies.add(enemy);
        }*/

        // TODO: make a factory of enemies and animate it

        this.parent.getChildren().addAll(enemies);
    }

    private void initSettings() {
        // ask SettingService to load data and return it as an HashMap<String,String> ( name, setting to apply

        if (this.settings != null) {
            throw new SettingsNotFound("Check the if the database connection");
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

    private void setScene(Parent parent) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double width = primaryScreenBounds.getWidth() / 1.8;
        double height = primaryScreenBounds.getHeight() / 1.8;

        this.scene = new Scene(parent, width, height);
    }

    private void setParent() {
        try {
            this.parent = FXMLLoader.load(getClass().getResource(Fxml.GAME.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
