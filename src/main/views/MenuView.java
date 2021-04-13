package main.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.controllers.GameController;
import main.controllers.MenuController;
import main.controllers.RankingController;
import main.exceptions.SceneNotFound;
import main.models.Game;
import main.models.Menu;
import main.models.Ranking;
import main.utils.enums.CurrentScene;
import main.utils.enums.Fxml;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;

public class MenuView extends View {
    private final static double BOUND_FACTOR = 1.8;
    private final Menu model;
    private final MenuController controller;

    private Stage stage;
    private Pane parent;
    private Scene scene;

    public MenuView(Menu model, MenuController controller, Stage stage) {
        this.model = model;
        this.controller = controller;

        this.model.addPropertyChangeListener(this);

        setStage(stage);
        setCurrentSceneListener();
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
        if (evt.getPropertyName().equals("currentScene")) {
            setCurrentSceneListener();
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

    private String getParentFromCurrentScene(CurrentScene newValue) {
        return newValue.toString().toLowerCase();
    }

    private View getView(CurrentScene scene) {
        return switch (scene) {
            case GAME -> new GameView(new Game(), new GameController());
            case MENU -> new MenuView(this.model, this.controller, this.stage);
            case SETTINGS -> new SettingsView();
            case RANKING -> new RankingView(new Ranking(), new RankingController());
            default -> throw new SceneNotFound(scene);
        };
    }

    private void setUpdatedStage(Scene scene) {
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void setCurrentSceneListener() {
        this.model.addPropertyChangeListener("currentScene", event -> {
            CurrentScene currentScene = (CurrentScene) event.getNewValue();
            setParent(getParentFromCurrentScene(currentScene));
            getView((CurrentScene) event.getNewValue());
            setScene();
            setUpdatedStage(getScene());
        });
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    void setScene() {
        this.scene = new Scene(this.parent, getWidth(), getHeight());
    }

    final void setParent(String filename) {
        try {
            this.parent = FXMLLoader.load(getClass().getResource(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
