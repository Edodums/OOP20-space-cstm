package main.models.settings;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 *
 */
@JsonDeserialize(using = TypeDeserializer.class)
public interface Type {}