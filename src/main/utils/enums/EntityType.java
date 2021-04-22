package main.utils.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import main.models.settings.Type;

@JsonDeserialize(as = EntityType.class)
public enum EntityType implements Type {
   PLAYER,
   COMMONSHIP,
   MOTHERSHIP;
   
   public static Type getType(String value) {
      return switch (value) {
        case "entityPlayer" -> EntityType.PLAYER;
        case "entityCommonShip" -> EntityType.COMMONSHIP;
        case "entityMotherShip" -> EntityType.MOTHERSHIP;
        default -> throw new IllegalStateException("Unexpected type value: " + value);
      };
   }
}
