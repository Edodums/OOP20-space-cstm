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
import main.models.settings.interfaces.Orientable;
import main.utils.enums.Orientations;
import main.views.customization.interfaces.CustomizableViewOrientation;

public class Orientation implements CustomizableViewOrientation, Initializable {
  private final ObservableList<Orientable> orientations = FXCollections.observableArrayList(Orientations.values());
  
  private Orientable orientationValue;
  
  @FXML
  private Group orientation;
  @FXML
  private Label orientationLabel;
  @FXML
  private ChoiceBox<Orientable> orientationsChoices;
  
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
  public Orientable getSelectedOrientation() {
    return this.orientationsChoices.getValue();
  }
  
  @Override
  public void setOrientationValue(Orientable value) {
    this.orientationValue = value;
  }
}
