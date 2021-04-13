package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.controllers.Menu;
import main.controllers.MenuController;
import main.models.Menu;
import main.utils.enums.CurrentScene;
import main.views.MenuView;

public class Main extends Application {

    public void start(Stage stage) {
        stage.setTitle("Space Invaders");

        Menu menu = new Menu(CurrentScene.MENU);

        new MenuView(menu, new MenuController(menu), stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
