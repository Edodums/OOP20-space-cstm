package main.views;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.controllers.*;
import main.models.Menu;
import main.utils.enums.CurrentScene;
import main.utils.enums.Fxml;

public class MenuView implements View {
    private static final String SCENE_PROPERTY = "currentScene";
    private static final double BOUND_FACTOR = 1.8;
    private static final Menu model = new Menu();
    private static final MenuController controller = new MenuController(model);

        private final Map<CurrentScene, View> views = new HashMap<>();

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
    public Pane getParent() {
        return this.parent;
    }
    
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @Override
    public Stage getStage() {
        return this.stage;
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
        return this.views.get(scene);
    }

    private void setNewScene(CurrentScene newScene) {
        setLoader(getFxmlFromCurrentScene(newScene));
        setLoaderFactory(newScene);
        setParent();
        
        final View view = getViewInstance(newScene);
        
        setScene(view);
        setUpdatedStage();
        view.setStage(getStage());
        
        if (view instanceof MovementHandler) {
            ((MovementHandler) view).movementHandler();
        }
    }

    private void setUpdatedStage() {
        getStage().setScene(getScene());
        getStage().show();
    }
    
    private void setScene(View viewInstance) {
        this.scene = new Scene(viewInstance.getParent(), viewInstance.getWidth(), viewInstance.getHeight());
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
    
    private void setLoaderFactory(CurrentScene currentScene)
    {
        this.loader.setControllerFactory(clazz -> {
            try {
                View view = views.get(currentScene);
    
                if (view != null) {
                    return view;
                }
                
                view = (View) clazz.getConstructor().newInstance();
                
                views.put(currentScene, view);
                
                return view;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            
            return null;
        });
    }
}
