package main.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ObservableModel {
    private PropertyChangeSupport support;

    protected void setSupport(PropertyChangeSupport support) {
        this.support = support;
    }

    public void firePropertyChange(String propertyName, Object field, Object value) {
        if (field.getClass() == value.getClass() && !propertyName.trim().isEmpty()) {
            this.support.firePropertyChange(propertyName, field, value);
        }
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeLister(PropertyChangeListener pcl) {
        this.support.removePropertyChangeListener(pcl);
    }
}
