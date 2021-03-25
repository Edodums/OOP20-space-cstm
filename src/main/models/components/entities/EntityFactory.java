package main.models.components.entities;

import main.exceptions.EntityNotFoundException;
import main.models.components.interfaces.Entity;
import main.utils.EntityType;

public class EntityFactory {
    public Entity getEntity(EntityType entityType) {
        switch (entityType) {
            case COMMONSHIP :
                return new CommonShip();
            default :
                throw new EntityNotFoundException();
        }
    }
}
