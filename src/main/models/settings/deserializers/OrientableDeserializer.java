package main.models.settings.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.models.settings.interfaces.Orientable;
import main.utils.enums.Orientations;

import java.io.IOException;

public class OrientableDeserializer extends JsonDeserializer<Orientable> {
  
  @Override
  public Orientable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    final ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
    final JsonNode root = (JsonNode) mapper.readTree(jsonParser);
    
    return mapper.readValue(root.toString(), Orientations.class);
  }
}
