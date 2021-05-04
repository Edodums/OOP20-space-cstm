package space.cstm.exceptions;

import space.cstm.utils.enums.CurrentScene;

public class SceneNotFound extends IllegalArgumentException{
    public SceneNotFound(CurrentScene scene){
        super(scene.name());
    }
}
