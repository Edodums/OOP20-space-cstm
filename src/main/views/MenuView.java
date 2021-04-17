package main.views;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.controllers.GameController;
import main.controllers.MenuController;
import main.controllers.RankingController;
import main.models.Game;
import main.models.Menu;
import main.models.Ranking;
import main.utils.enums.CurrentScene;
import main.utils.enums.Fxml;

public class MenuView implements View {
    private static final String SCENE_PROPERTY = "currentScene";
    private static final double BOUND_FACTOR = 1.8;

    private Menu model;
    private MenuController controller;

    private Stage stage;
    private Scene scene;
    private GridPane parent;

    public MenuView() {}

    public MenuView(Menu model, MenuController controller, Stage stage) {
        this.model = model;
        this.controller = controller;
        this.stage = stage;

        this.model.addPropertyChangeListener(this);
        this.controller.setCurrentScene(CurrentScene.MENU);
    }

    @FXML
    public void start(ActionEvent actionEvent) {
        changeScene(Fxml.GAME.toString());
    }

    @FXML
    public void settings(ActionEvent actionEvent) {
        changeScene(Fxml.SETTINGS.toString());
    }

    @FXML
    public void ranking(ActionEvent actionEvent){
        changeScene(Fxml.RANKING.toString());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(SCENE_PROPERTY)) {
            setNewScene((CurrentScene) evt.getNewValue());
        }
    }

    @Override
    public double getBoundFactor() {
        return BOUND_FACTOR;
    }

    @Override
    public Parent getParent() {
        return this.parent;
    }

    private void changeScene(String filename) {
        this.controller.setCurrentScene(CurrentScene.valueOf(filename));
    }

    Scene getScene() {
        return this.scene;
    }

    private String getFxmlFromCurrentScene(CurrentScene currentScene) {
        return Fxml.valueOf(currentScene.toString()).toString();
    }

    private void getViewInstance(CurrentScene scene) {
        switch (scene) {
            case GAME -> new GameView(new Game(), new GameController());
            // case SETTINGS -> new SettingsView();
            case RANKING -> new RankingView(new Ranking(), new RankingController());
        }
    }

    private void setUpdatedStage(Scene scene) {
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void setNewScene(CurrentScene newScene) {
        setParent(getFxmlFromCurrentScene(newScene));
        getViewInstance(newScene);
        setScene();
        setUpdatedStage(getScene());
    }

    void setScene() {
        this.scene = new Scene(this.parent, getWidth(), getHeight());
    }

    private void setParent(String filename) {
        try {
            this.parent = FXMLLoader.load(getClass().getResource(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}