package main.models;

import main.models.settings.Grid;
import main.models.settings.Type;
import main.utils.enums.EntityType;
import main.utils.enums.WeaponType;


public class EntityImage {

    private final String name;
    private final Type<EntityType, WeaponType> type;
    private final Grid grid;

    public EntityImage (String name,Type<EntityType,WeaponType> type, Grid grid){
         this.name = name;
         this.type = type;
         this.grid = grid;
    }

    public String getName() {
        return name;
    }

    public Type<EntityType, WeaponType> getType() {
        return type;
    }
}
