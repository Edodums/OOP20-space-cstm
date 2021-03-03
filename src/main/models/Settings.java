package main.models;

import main.utils.Orientations;

public class Settings extends Model {

    private final Orientations orientation;
    private final EntityImage entityImage;

    public Settings(Orientations orientation, EntityImage entityImage){
        this.orientation = orientation;
        this.entityImage = entityImage;
    }


}
