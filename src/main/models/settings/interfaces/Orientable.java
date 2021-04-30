package main.models.settings.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import main.models.settings.deserializers.OrientableDeserializer;

@JsonDeserialize(using = OrientableDeserializer.class)
public interface Orientable {
}
