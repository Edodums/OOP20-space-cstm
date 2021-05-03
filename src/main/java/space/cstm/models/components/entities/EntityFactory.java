package space.cstm.models.components.entities;

import space.cstm.exceptions.EntityNotFoundException;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.utils.enums.EntityType;

public class EntityFactory {

    public Entity getEntity(CustomizableTypeImage entityImage) {
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