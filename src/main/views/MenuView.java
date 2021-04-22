package main.views;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.controllers.*;
import main.models.Game;
import main.models.Menu;
import main.models.Ranking;
import main.utils.enums.CurrentScene;
import main.utils.enums.Fxml;

public class MenuView implements View {
    private static final String SCENE_PROPERTY = "currentScene";
    private static final double BOUND_FACTOR = 1.8;
    private static final Menu model = new Menu();
    private static final MenuController controller = new MenuController(model);

    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;

    @FXML
    private Pane parent;

    public MenuView() {}

    public MenuView(Stage stage) {
        setStage(stage);
        model.addPropertyChangeListener(this);
        controller.setCurrentScene(CurrentScene.MENU);
    }

    @FXML
    public void start(ActionEvent actionEvent) {
        changeScene(Fxml.GAME);
    }

    @FXML
    public void settings(ActionEvent actionEvent) {
        changeScene(Fxml.SETTINGS);
    }

    @FXML
    public void ranking(ActionEvent actionEvent){
        changeScene(Fxml.RANKING);
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

    private Scene getScene() {
        return this.scene;
    }

    private void changeScene(Fxml fxml) {
        controller.setCurrentScene(CurrentScene.valueOf(fxml.name()));
    }

    private String getFxmlFromCurrentScene(CurrentScene currentScene) {
        return Fxml.valueOf(currentScene.toString()).getFilePath();
    }

    private View getViewInstance(CurrentScene scene) {
        return switch (scene) {
            case GAME -> new GameView(new Game(), (GameController) getController());
            // case SETTINGS -> new SettingsView();
            case RANKING -> new RankingView(new Ranking(), (RankingController) getController());
            case MENU -> new MenuView(this.stage);
        };
    }

    private void setNewScene(CurrentScene newScene) {
        setLoader(getFxmlFromCurrentScene(newScene));
        setParent();
        getViewInstance(newScene);
        setScene();
        setUpdatedStage();
    }

    private void setUpdatedStage() {
        getStage().setScene(getScene());
        getStage().show();
    }

    private Controller getController() {
        return this.loader.getController();
    }

    private void setScene() {
        this.scene = new Scene(getParent(), getWidth(), getHeight());
    }

    private void setParent() {
        try {
            this.parent = this.loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLoader(String filename) {
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(filename)));
    }

    private void setStage(final Stage stage) {
        this.stage = stage;
    }

    private Stage getStage() {
        return this.stage;
    }
}
