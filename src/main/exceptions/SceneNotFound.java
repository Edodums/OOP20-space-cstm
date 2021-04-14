package main.exceptions;

import main.utils.enums.CurrentScene;

public class SceneNotFound extends IllegalArgumentException{
    public SceneNotFound(CurrentScene scene){
        super(scene.name());
    }
}
