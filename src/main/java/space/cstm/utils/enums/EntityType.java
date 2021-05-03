package space.cstm.utils.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import space.cstm.models.settings.interfaces.Type;

@JsonDeserialize(as = EntityType.class)
public enum EntityType implements Type {
   PLAYER,
   COMMONSHIP,
   MOTHERSHIP;
   
   public static Type getType(String value) {
     Type type;
     
     switch (value) {
       case "entityPlayer":
         type = EntityType.PLAYER;
         break;
       case "entityCommonShip":
         type = EntityType.COMMONSHIP;
         break;
       case "entityMotherShip":
         type = EntityType.MOTHERSHIP;
         break;
       default: throw new IllegalStateException("Unexpected type value: " + value);
     };
     
     return type;
   }
}
