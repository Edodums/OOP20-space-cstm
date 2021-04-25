package main.views;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;

public interface View extends PropertyChangeListener {
  Pane getParent();
  
  double getBoundFactor();
  
  void setStage(Stage stage);
  
  Stage getStage();
  
  default double getWidth() {
    return getPrimaryScreenBounds().getWidth() / getBoundFactor();
  }
  
  default double getHeight() {
    return getPrimaryScreenBounds().getHeight() / getBoundFactor();
  }
  
  /* Primary Screen (useful if the user has multiple monitors bounds */
  default Rectangle2D getPrimaryScreenBounds() {
    return Screen.getPrimary().getVisualBounds();
  }
}
