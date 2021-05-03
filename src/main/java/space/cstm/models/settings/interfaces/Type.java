package space.cstm.models.settings.interfaces;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import space.cstm.models.settings.deserializers.TypeDeserializer;

@JsonDeserialize(using = TypeDeserializer.class)
public interface Type {}