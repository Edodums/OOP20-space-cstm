package main.models.settings.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.models.settings.Settings;
import main.models.settings.interfaces.Setting;

import java.io.IOException;

public class SettingDeserializer extends JsonDeserializer<Setting> {
  @Override
  public Setting deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    final ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
    final JsonNode root = (JsonNode) mapper.readTree(jsonParser);
    
    TypeReference<Settings> settingsTypeReference = new TypeReference<Settings>(){};
    
    return mapper.readValue(String.valueOf(root), settingsTypeReference);
  }
}
