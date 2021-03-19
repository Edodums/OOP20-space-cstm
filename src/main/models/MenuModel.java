package main.models;

import main.utils.CurrentScene;

public class MenuModel extends Model{

    private CurrentScene currentScene;

    public MenuModel(CurrentScene currentScene){
        this.currentScene = currentScene;
    }

    public CurrentScene getCurrentScene(){
        return currentScene;
    }
    public void setCurrentScene(CurrentScene currentScene){
        this.currentScene = currentScene;
    }
}
