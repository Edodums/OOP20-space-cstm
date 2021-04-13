package main.models;

import main.utils.enums.CurrentScene;

public class Menu extends ObservableModel{

    private CurrentScene currentScene;

    public Menu(CurrentScene currentScene){
        setCurrentScene(currentScene);
    }

    public CurrentScene getCurrentScene(){
        return currentScene;
    }

    public void setCurrentScene(CurrentScene currentScene){
        this.currentScene = currentScene;
        firePropertyChange("currentScene", this.currentScene, currentScene);
    }
}
