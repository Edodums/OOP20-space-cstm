package main.models.components.entities;

import main.exceptions.EntityNotFoundException;
import main.models.settings.TypeImage;
import main.models.components.interfaces.Entity;
import main.utils.enums.EntityType;

public class EntityFactory {
  
  public Entity getEntity(TypeImage entityImage) {
    final EntityType types = (EntityType) entityImage.getType();
    Entity entity;
  
    switch (types) {
      case COMMONSHIP:
                 entity = new CommonShip(entityImage);
                 break;
      case MOTHERSHIP:
                 entity = new MotherShip(entityImage);
                 break;
      case PLAYER:
                 entity = new PlayerShip(entityImage);
                 break;
      default:   throw new EntityNotFoundException();
    }
    
    return entity;
  }
}
