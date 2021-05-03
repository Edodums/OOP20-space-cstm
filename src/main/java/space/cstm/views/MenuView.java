package space.cstm.views;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import space.cstm.controllers.*;
import space.cstm.models.Menu;
import space.cstm.utils.enums.CurrentScene;
import space.cstm.utils.enums.Fxml;

public class MenuView implements View {
    private static final String SCENE_PROPERTY = "currentScene";
    private static final float BOUND_FACTOR = 1.8f;
    private static final Menu model = new Menu();
    private static final MenuController controller = new MenuController(model);

    private final Set<View> views = new LinkedHashSet<>();

    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;
    
    @FXML
    private Pane parent;

    public MenuView() {}

    public MenuView(Stage stage) {
        setStage(stage);
        addListenerToModel(model);
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
    public float getBoundFactor() {
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

    public void changeScene(Fxml fxml) {
        controller.setCurrentScene(CurrentScene.valueOf(fxml.name()));
    }
    
    private String getFxmlFromCurrentScene(CurrentScene currentScene) {
        return Fxml.valueOf(currentScene.toString()).getFilePath();
    }

    private View getViewInstance() {
        Iterator<View> it = this.views.iterator();
        View view = null;
    
        while (it.hasNext()) {
            view = it.next();
        }
        
        return view;
    }

    public static void goToScene(CurrentScene newScene) {
        controller.setCurrentScene(newScene);
    }
    
    private void setNewScene(CurrentScene newScene) {
        setLoader(getFxmlFromCurrentScene(newScene));
        setLoaderFactory();
        setParent();
        
        final View view = getViewInstance();
        
        setScene(view);
        
        setUpdatedStage();
        view.setStage(getStage());
        
        if (view instanceof KeyEventListener) {
            ((KeyEventListener) view).addKeyEventHandler();
            ((KeyEventListener) view).keyListener();
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
    
    private void setLoaderFactory()
    {
        this.loader.setControllerFactory(clazz -> {
            try {
                View view = (View) clazz.getConstructor().newInstance();
                
                views.add(view);
                
                return view;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            
            return null;
        });
    }
}
