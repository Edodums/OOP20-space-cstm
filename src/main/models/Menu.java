package main.models;

import main.utils.enums.CurrentScene;
import java.beans.PropertyChangeSupport;

public class Menu implements ObservableModel{

    private CurrentScene currentScene;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public Menu(){
        //empty
    }

    public CurrentScene getCurrentScene(){
        return currentScene;
    }

    public void setCurrentScene(CurrentScene currentScene){
        firePropertyChange("currentScene", this.currentScene, currentScene);
        this.currentScene = currentScene;
    }

    @Override
    public PropertyChangeSupport getSupport() {
        return this.support;
    }
}
