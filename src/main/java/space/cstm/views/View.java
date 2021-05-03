package space.cstm.views;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import space.cstm.models.ObservableModel;

import java.beans.PropertyChangeListener;

public interface View extends PropertyChangeListener {

  Pane getParent();
  
  float getBoundFactor();
  
  void setStage(Stage stage);
  
  Stage getStage();
  
  default float getWidth() {
    return (float) getParent().getPrefWidth();
    // return getPrimaryScreenBounds().getWidth() / getBoundFactor();
  }
  
  default float getHeight() {
    return (float) getParent().getPrefHeight();
    // return getPrimaryScreenBounds().getHeight() / getBoundFactor();
  }
  
  /* Primary Screen (useful if the user has multiple monitors bounds */
  default Rectangle2D getPrimaryScreenBounds() {
    return Screen.getPrimary().getVisualBounds();
  }

  default void addListenerToModel(ObservableModel model) {
    model.addPropertyChangeListener(this);
  }
}
