package main.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.utils.Fxml;


import java.io.IOException;

public class Menu {

    private Stage stage;
    private Scene scene;
    private AnchorPane parent;

    @FXML
    private Button start;

    @FXML
    private Button settings;

    @FXML
    private Button ranking;

    public Menu() {}

    public Menu(Stage stage) {
        setParent();
        setScene(this.parent);

        stage.setScene(this.scene);
        stage.show();
    }

    private void setScene(Parent parent) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double width = primaryScreenBounds.getWidth() / 1.8;
        double height = primaryScreenBounds.getHeight() / 1.8;

        this.scene = new Scene(parent, width, height);
    }

    private void setParent() {
        try {
            this.parent = FXMLLoader.load(getClass().getResource(Fxml.MENU.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Fxml.GAME.toString()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
