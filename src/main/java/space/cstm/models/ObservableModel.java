package space.cstm.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public interface ObservableModel {
    /**
     *
     * @return
     */
    PropertyChangeSupport getSupport();

    /**
     *
     * @param propertyName
     * @param oldValue
     * @param newValue
     */
    default void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        getSupport().firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     *
     * @param listener
     */
    default void addPropertyChangeListener(PropertyChangeListener listener) {
        getSupport().addPropertyChangeListener(listener);
    }

    /**
     *
     * @param propertyName
     * @param listener
     */
    default void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getSupport().addPropertyChangeListener(propertyName, listener);
    }

    /**
     *
     * @param pcl
     */
    default void removePropertyChangeLister(PropertyChangeListener pcl) {
        getSupport().removePropertyChangeListener(pcl);
    }
}
