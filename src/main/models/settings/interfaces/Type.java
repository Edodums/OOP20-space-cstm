package main.models.settings.interfaces;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import main.models.settings.deserializers.TypeDeserializer;

@JsonDeserialize(using = TypeDeserializer.class)
public interface Type {}