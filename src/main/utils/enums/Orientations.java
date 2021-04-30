package main.utils.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import main.models.settings.interfaces.Orientable;

@JsonDeserialize(as = Orientations.class)
public enum Orientations implements Orientable {
    HORIZONTAL,
    VERTICAL;
    
    public static Orientable getOrientation(String value) {
        return Orientations.valueOf(value);
    }
}
