package main.models.components.entities;

import main.exceptions.EntityNotFoundException;
import main.models.EntityImage;
import main.models.components.interfaces.Entity;

public class EntityFactory {
  public Entity getEntity(EntityImage entityImage) {
    switch (entityImage.getEntityType()) {
      case COMMONSHIP :
        return new CommonShip(entityImage);
      default :
        throw new EntityNotFoundException();
    }
  }
}
