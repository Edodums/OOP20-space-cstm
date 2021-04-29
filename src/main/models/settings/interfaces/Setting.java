package main.models.settings.interfaces;

import main.utils.enums.Orientations;
import java.util.Map;

public interface Setting {

    void replaceTypeImage(final String key, final CustomizableTypeImage typeImage);

    Map<String, CustomizableTypeImage> getTypeImages();

    Orientations getOrientation();

    void setOrientation(final Orientations orientation);

    void setTypeImages(final Map<String, CustomizableTypeImage> typeImages);

    void loadDefault();

}
