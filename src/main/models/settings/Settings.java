package main.models.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import main.models.ObservableModel;
import main.models.settings.interfaces.CustomizableTypeImage;
import main.models.settings.interfaces.Setting;
import main.utils.enums.Orientations;

public class Settings implements ObservableModel, Setting {
    @JsonIgnore
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    @JsonSerialize
    private Orientations orientation;
    
    @JsonSerialize
    private Map<String, CustomizableTypeImage> typeImages = Collections.synchronizedMap(new WeakHashMap<>());
    
    public Settings() {}

    @Override
    public Map<String, CustomizableTypeImage> getTypeImages() {
        return this.typeImages;
    }

    @Override
    public Orientations getOrientation() {
        return this.orientation;
    }
    
    @Override
    public PropertyChangeSupport getSupport() {
        return this.support;
    }

    @Override
    public void setOrientation(final Orientations orientation) {
        Orientations oldValue = this.orientation;
        this.orientation = orientation;
        
        firePropertyChange("orientation", oldValue, orientation);
    }

    @Override
    public void setTypeImages(final Map<String, CustomizableTypeImage> typeImages) {
        Map<String, CustomizableTypeImage> oldMap = this.typeImages;
        
        this.typeImages = typeImages;
        
        firePropertyChange("typeImages", oldMap, typeImages);
    }

    @Override
    public void replaceTypeImage(final String key, final CustomizableTypeImage typeImage) {
        Map<String, CustomizableTypeImage> oldMap = this.typeImages;
        
        this.typeImages.replace(key, typeImage);
    
        firePropertyChange("typeImages", oldMap, this.typeImages);
    }

    @Override
    public void loadDefault() {
        if (CustomizeDefaults.areDefaultsNeeded(this)) {
            CustomizeDefaults.loadDefaults(this);
        }
    }
}
