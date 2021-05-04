package space.cstm.views.customization.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import space.cstm.exceptions.DirectoryNotCreated;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.models.settings.interfaces.GridImage;
import space.cstm.services.FileService;
import space.cstm.utils.enums.ResourcePath;
import space.cstm.views.customization.interfaces.CustomizableViewTypeImage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WeaponPlayer implements CustomizableViewTypeImage, Initializable {
  private CustomizableTypeImage current;

  @FXML
  private TextField weaponPlayerFilename;
  @FXML
  public Group weaponPlayer;
  @FXML
  private Label weaponPlayerLabel;
  @FXML
  private CheckBox weaponPlayerIsDivided;
  @FXML
  private ImageView weaponPlayerImage;
  @FXML
  private TextField weaponPlayerSelectedColumn;
  @FXML
  private TextField weaponPlayerSelectedRow;
  @FXML
  private TextField weaponPlayerColumns;
  @FXML
  private TextField weaponPlayerRows;
  @FXML
  private TextField weaponPlayerInternalColumns;
  
  public WeaponPlayer() {}

  @FXML
  public void weaponPlayerChooseImage() {
    File selectedFile = getFileChooser().showOpenDialog(this.weaponPlayer.getScene().getWindow());

    if (selectedFile != null) {
      try {
        String filename = FileService.saveFile(selectedFile, ResourcePath.EXTERNAL_IMAGES_PATH.toString(),true);
        setFilename(filename);
      } catch (IllegalAccessException | DirectoryNotCreated | IOException e) {
        e.printStackTrace();
      }

      final Image image = new Image(selectedFile.toURI().toString());
      setImage(image);
    }
  }
  
  @Override
  public String getLabel() {
    return this.weaponPlayerLabel.getText();
  }
  
  @Override
  public String getGroupId() {
    return this.weaponPlayer.getId();
  }
  
  @Override
  public Integer getRows() {
    return getNumericValue(this.weaponPlayerRows.getText());
  }
  
  @Override
  public Integer getColumns() {
    return getNumericValue(this.weaponPlayerColumns.getText());
  }
  
  @Override
  public Integer getSelectedRow() {
    return getNumericValue(this.weaponPlayerSelectedRow.getText());
  }
  
  @Override
  public Integer getSelectedColumn() {
    return getNumericValue(this.weaponPlayerSelectedColumn.getText());
  }

  @Override
  public Integer getInternalColumns() {
    return getNumericValue(this.weaponPlayerInternalColumns.getText());
  }

  @Override
  public void toggleGridTextFields(boolean value) {
    this.weaponPlayerRows.setDisable(!value);
    this.weaponPlayerColumns.setDisable(!value);
    this.weaponPlayerSelectedRow.setDisable(!value);
    this.weaponPlayerSelectedColumn.setDisable(!value);
    this.weaponPlayerInternalColumns.setDisable(!value);

    final float opacityValue = value ? 1.0f : 0.0f;

    this.weaponPlayerRows.setOpacity(opacityValue);
    this.weaponPlayerColumns.setOpacity(opacityValue);
    this.weaponPlayerSelectedRow.setOpacity(opacityValue);
    this.weaponPlayerSelectedColumn.setOpacity(opacityValue);
    this.weaponPlayerInternalColumns.setOpacity(opacityValue);
  }
  
  @Override
  public void handleRows() {
    this.weaponPlayerRows.textProperty().addListener((observable, oldValue, newValue) -> setRows(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleColumns() {
    this.weaponPlayerColumns.textProperty().addListener((observable, oldValue, newValue) -> setColumns(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleSelectedRow() {
    this.weaponPlayerSelectedRow.textProperty().addListener((observable, oldValue, newValue) -> setSelectedRow(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleSelectedColumn() {
    this.weaponPlayerSelectedColumn.textProperty().addListener((observable, oldValue, newValue) -> setSelectedColumn(getFormattedNumericValue(newValue)));
  }

  @Override
  public void handleInternalColumns() {
    this.weaponPlayerInternalColumns.textProperty().addListener((observable, oldValue, newValue) -> setInternalColumns(getFormattedNumericValue(newValue)));
  }

  @Override
  public void setRows(String value) {
    this.weaponPlayerRows.setText(value);
  }
  
  @Override
  public void setColumns(String value) {
    this.weaponPlayerColumns.setText(value);
  }
  
  @Override
  public void setSelectedRow(String value) {
    this.weaponPlayerSelectedRow.setText(value);
  }
  
  @Override
  public void setSelectedColumn(String value) {
    this.weaponPlayerSelectedColumn.setText(value);
  }

  @Override
  public void setInternalColumns(String value) {
    this.weaponPlayerInternalColumns.setText(value);
  }

  @Override
  public void setTypeImage(CustomizableTypeImage typeImage) {
    this.current = typeImage;
  }

  @Override
  public CustomizableTypeImage getTypeImages() {
    return this.current;
  }
  
  @Override
  public void setImage(Image image) {
    this.weaponPlayerImage.setImage(image);
  }
  
  @Override
  public void setFilename(String filename) {
    this.weaponPlayerFilename.setText(filename);
  }

  @Override
  public String getFilename() {
    return this.weaponPlayerFilename.getText();
  }

  @Override
  public void setDefaults() {
    final GridImage grid = getTypeImages().getGrid();

    this.weaponPlayerFilename.setText(getTypeImages().getName());
    this.weaponPlayerImage.setImage(new Image(getTypeImages().getName()));

    this.weaponPlayerRows.setText(String.valueOf(grid.getRows()));
    this.weaponPlayerColumns.setText(String.valueOf(grid.getColumns()));
    this.weaponPlayerSelectedRow.setText(String.valueOf(grid.getSelectedRow()));
    this.weaponPlayerSelectedColumn.setText(String.valueOf(grid.getSelectedColumn()));
    this.weaponPlayerInternalColumns.setText(String.valueOf(grid.getInternalColumns()));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    this.weaponPlayerIsDivided.selectedProperty().addListener((observable, oldValue, newValue) -> toggleGridTextFields(observable.getValue()));

    handleRows();
    handleColumns();
    handleSelectedRow();
    handleSelectedColumn();
    handleInternalColumns();
  }
}
