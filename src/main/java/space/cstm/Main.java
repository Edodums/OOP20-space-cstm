package space.cstm;

import javafx.application.Application;
import javafx.stage.Stage;
import space.cstm.views.MenuView;

public class Main extends Application {

    public void start(Stage stage) {
        stage.setTitle("Space Invaders");
        new MenuView(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
