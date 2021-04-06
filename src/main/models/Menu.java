package main.models;

import main.utils.CurrentScene;

public class Menu extends ObservableModel{

    private CurrentScene currentScene;

    public Menu(CurrentScene currentScene){
        this.currentScene = currentScene;
    }

    public CurrentScene getCurrentScene(){
        return currentScene;
    }
    public void setCurrentScene(CurrentScene currentScene){
        this.currentScene = currentScene;
    }
}
