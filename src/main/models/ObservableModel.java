package main.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public interface ObservableModel {
     PropertyChangeSupport getSupport();

    default void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        getSupport().firePropertyChange(propertyName, oldValue, newValue);
    }

    default void addPropertyChangeListener(PropertyChangeListener listener) {
        getSupport().addPropertyChangeListener(listener);
    }

    default void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getSupport().addPropertyChangeListener(propertyName, listener);
    }

    default void removePropertyChangeLister(PropertyChangeListener pcl) {
        getSupport().removePropertyChangeListener(pcl);
    }
}
