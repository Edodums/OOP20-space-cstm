package main.models;

import main.utils.enums.Orientations;

import java.util.List;

public class Settings extends ObservableModel {

    private final Orientations orientation;
    private final List<EntityImage> entityImage;

    public Settings(Orientations orientation, List<EntityImage> entityImage){
        this.orientation = orientation;
        this.entityImage = entityImage;
    }

    public List<EntityImage> getEntityImage() {
        return entityImage;
    }
}
