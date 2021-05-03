package space.cstm.models.settings.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import space.cstm.models.settings.deserializers.OrientableDeserializer;

@JsonDeserialize(using = OrientableDeserializer.class)
public interface Orientable {
}
