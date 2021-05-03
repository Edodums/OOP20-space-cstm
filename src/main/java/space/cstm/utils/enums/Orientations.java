package space.cstm.utils.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import space.cstm.models.settings.interfaces.Orientable;

@JsonDeserialize(as = Orientations.class)
public enum Orientations implements Orientable {
    HORIZONTAL,
    VERTICAL;
    
    public static Orientable getOrientation(String value) {
        return Orientations.valueOf(value);
    }
}
