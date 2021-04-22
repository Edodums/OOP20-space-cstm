package main.views.customization.components;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import main.utils.enums.Orientations;
import main.views.customization.interfaces.CustomizableOrientation;

public class Orientation implements CustomizableOrientation, Initializable {
  private final ObservableList<Orientations> orientations = FXCollections.observableArrayList(Orientations.values());
  
  private Orientations orientationValue;
  
  @FXML
  private Group orientation;
  @FXML
  private Label orientationLabel;
  @FXML
  private ChoiceBox<Orientations> orientationsChoices;
  
  public Orientation() {}
  
  @Override
  public String getLabel() {
    return this.orientationLabel.getText();
  }
  
  @Override
  public String getGroupId() {
    return this.orientation.getId();
  }
  
  @Override
  public void setDefaults() {
    this.orientationsChoices.setItems(this.orientations);
    this.orientationsChoices.setValue(this.orientationValue);
    handleSelectOrientation();
  }
  
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }
  
  @Override
  public void handleSelectOrientation() {
    this.orientationsChoices
          .getSelectionModel()
          .selectedIndexProperty()
          .addListener((observableValue, oldIndex, newIndex) -> setSelectedOrientation((Integer) newIndex));
  }
  
  @Override
  public void setSelectedOrientation(int index) {
    this.orientationsChoices.getSelectionModel().select(index);
  }
  
  @Override
  public Orientations getSelectedOrientation() {
    return this.orientationsChoices.getValue();
  }
  
  public void setOrientationValue(Orientations value) {
    this.orientationValue = value;
  }
}
