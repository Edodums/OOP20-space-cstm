package main.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import main.models.settings.Type;

@JsonDeserialize(as = WeaponType.class)
public enum WeaponType implements Type {
    @JsonProperty("weaponNpc")
    NPC,
    @JsonProperty("weaponPlayer")
    PLAYER;
    
    public static Type getType(String value) {
        Type type;
        
        switch (value) {
            case "weaponPlayer":
                type = WeaponType.PLAYER;
                break;
            case "weaponNpc":
                type = WeaponType.NPC;
                break;
            default: throw new IllegalStateException("Unexpected type value: " + value);
        };
        
        return type;
    }
}
