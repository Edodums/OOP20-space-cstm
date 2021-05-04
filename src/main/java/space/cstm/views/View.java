package space.cstm.views;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import space.cstm.models.ObservableModel;

import java.beans.PropertyChangeListener;

/**
 * The View of the MVC pattern has also the functionality to make a class an Observer
 */
public interface View extends PropertyChangeListener {

  /**
   *
   * @return the parent of the fxml file that is currently loaded
   */
  Pane getParent();

  /**
   *
   * @param stage
   */
  void setStage(Stage stage);

  /**
   *
   * @return the current stage of loaded view
   */
  Stage getStage();

  /**
   *
   * @return pref width of the current loaded fxml file
   */
  default float getWidth() {
    return (float) getParent().getPrefWidth();
  }

  /**
   * @return pref width of the current loaded fxml file
   */
  default float getHeight() {
    return (float) getParent().getPrefHeight();
  }

  /**
   * Using the JavaBeans package this method can make a concrete View class an Observer
   * @param model
   */
  default void addListenerToModel(ObservableModel model) {
    model.addPropertyChangeListener(this);
  }
}
