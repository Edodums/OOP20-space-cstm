package main.models.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import main.models.ObservableModel;
import main.utils.enums.Orientations;

/**
 *
 */
public class Settings implements ObservableModel {
    @JsonIgnore
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    @JsonSerialize
    private Orientations orientation;
    
    @JsonSerialize
    private Map<String, TypeImage> typeImages = Collections.synchronizedMap(new WeakHashMap<>());
    
    public Settings() {}
    
    public Map<String, TypeImage> getTypeImages() {
        return this.typeImages;
    }
    
    public Orientations getOrientation() {
        return this.orientation;
    }
    
    @Override
    public PropertyChangeSupport getSupport() {
        return this.support;
    }
    
    public void setOrientation(final Orientations orientation) {
        Orientations oldValue = this.orientation;
        this.orientation = orientation;
        
        firePropertyChange("orientation", oldValue, orientation);
    }
    
    public void setTypeImages(final Map<String, TypeImage> typeImages) {
        Map<String, TypeImage> oldMap = this.typeImages;
        
        this.typeImages = typeImages;
        
        firePropertyChange("typeImages", oldMap, typeImages);
    }
    
    public void replaceTypeImage(final String key, final TypeImage typeImage) {
        Map<String, TypeImage> oldMap = this.typeImages;
        
        this.typeImages.replace(key, typeImage);
    
        firePropertyChange("typeImages", oldMap, this.typeImages);
    }
    
    public void loadDefault() {
        if (CustomizeDefaults.areDefaultsNeeded(this)) {
            CustomizeDefaults.loadDefaults(this);
        }
    }
}
