package main.models.settings.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.models.settings.interfaces.Type;
import main.utils.enums.EntityType;
import main.utils.enums.WeaponType;

import java.io.IOException;

public class TypeDeserializer extends JsonDeserializer<Type> {
  @Override
  public Type deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    final ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
    final JsonNode root = (JsonNode) mapper.readTree(jsonParser);
    final String parent = jsonParser.getParsingContext().getParent().getCurrentName();
    
    if (jsonParser.getCurrentName().equals("type")) {
      if (parent.contains("weapon")) {
        return mapper.readValue(root.toString(), WeaponType.class);
      }
  
      return mapper.readValue(root.toString(), EntityType.class);
    }
    
    return null;
  }
}