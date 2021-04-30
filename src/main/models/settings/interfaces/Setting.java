package main.models.settings.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import main.models.settings.Settings;
import main.models.settings.deserializers.SettingDeserializer;

import java.util.Map;

@JsonDeserialize(as = Settings.class)
public interface Setting {

    void replaceTypeImage(final String key, final CustomizableTypeImage typeImage);
    
    Map<String, CustomizableTypeImage> getTypeImages();
    
    Orientable getOrientation();
    
    void setOrientation(final Orientable orientation);
    
    void setTypeImages(final Map<String, CustomizableTypeImage> typeImages);

    void loadDefault();

}
