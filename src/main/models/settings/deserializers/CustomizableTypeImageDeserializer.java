package main.models.settings.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.models.settings.TypeImage;
import main.models.settings.interfaces.CustomizableTypeImage;

import java.io.IOException;

public class CustomizableTypeImageDeserializer extends JsonDeserializer<CustomizableTypeImage> {
  @Override
  public CustomizableTypeImage deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    final ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
    final JsonNode root = (JsonNode) mapper.readTree(jsonParser);
    
    return mapper.readValue(jsonParser, TypeImage.class);
  }
}
