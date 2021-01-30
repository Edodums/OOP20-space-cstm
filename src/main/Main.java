package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.controllers.Menu;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Space Invaders");


        new Menu(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
