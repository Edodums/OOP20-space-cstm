package main.models;

import main.utils.EntityType;
import main.utils.SpriteType;

public class EntityImage {

    private final String name;
    private final EntityType entityType;
    private final SpriteType spriteType;

    public EntityImage (String name,EntityType entityType, SpriteType spriteType){
         this.name = name;
         this.entityType = entityType;
         this.spriteType = spriteType;
    }
}
