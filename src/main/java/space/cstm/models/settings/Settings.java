package space.cstm.models.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import space.cstm.models.ObservableModel;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.models.settings.interfaces.Orientable;
import space.cstm.models.settings.interfaces.Setting;
import space.cstm.models.settings.interfaces.Type;

public class Settings implements ObservableModel, Setting {
    @JsonIgnore
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    @JsonSerialize
    private Orientable orientation;
    
    @JsonSerialize
    private Map<String, CustomizableTypeImage> typeImages = Collections.synchronizedMap(new WeakHashMap<>());
    
    public Settings() {}

    @Override
    public Map<String, CustomizableTypeImage> getTypeImages() {
        return this.typeImages;
    }

    @Override
    public Orientable getOrientation() {
        return this.orientation;
    }
    
    @Override
    public PropertyChangeSupport getSupport() {
        return this.support;
    }

    @Override
    public void setOrientation(final Orientable orientation) {
        Orientable oldValue = this.orientation;
        
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
