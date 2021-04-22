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
        return switch (value) {
            case "weaponPlayer" -> WeaponType.PLAYER;
            case "weaponNpc" -> WeaponType.NPC;
            default -> throw new IllegalStateException("Unexpected type value: " + value);
        };
    }
}
