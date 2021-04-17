package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.controllers.MenuController;
import main.models.Menu;
import main.views.MenuView;

public class Main extends Application {

    public void start(Stage stage) {
        stage.setTitle("Space Invaders");

        Menu menu = new Menu();
        MenuController controller = new MenuController(menu);

        new MenuView(menu, controller, stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
