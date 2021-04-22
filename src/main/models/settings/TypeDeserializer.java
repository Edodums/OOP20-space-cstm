package main.models.settings;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.utils.enums.EntityType;
import main.utils.enums.WeaponType;

import java.io.IOException;

public class TypeDeserializer extends JsonDeserializer<Type> {

  @Override
  public Type deserialize(JsonParser jp, DeserializationContext context) throws IOException {
    final ObjectMapper mapper = (ObjectMapper) jp.getCodec();
    final JsonNode root = (JsonNode) mapper.readTree(jp);
    final String parent = jp.getParsingContext().getParent().getCurrentName();
    
    if (jp.getCurrentName().equals("type") && parent.contains("weapon")) {
      return mapper.readValue(root.toString(), WeaponType.class);
    }
    
    return mapper.readValue(root.toString(), EntityType.class);
  }

}
