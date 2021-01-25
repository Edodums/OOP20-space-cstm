package main;

import main.controllers.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Space Invaders");
        // TODO: navigation settings
        new Game(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
