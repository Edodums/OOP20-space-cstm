package space.cstm.views.customization.components;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import space.cstm.exceptions.DirectoryNotCreated;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.models.settings.interfaces.GridImage;
import space.cstm.services.FileService;
import space.cstm.utils.enums.ResourcePath;
import space.cstm.views.customization.interfaces.CustomizableViewTypeImage;

public class WeaponNpc implements CustomizableViewTypeImage, Initializable {
  private CustomizableTypeImage current;
  
  @FXML
  private TextField weaponNpcFilename;
  @FXML
  private Group weaponNpc;
  @FXML
  private Label weaponNpcLabel;
  @FXML
  private CheckBox weaponNpcIsDivided;
  @FXML
  private ImageView weaponNpcImage;
  @FXML
  private TextField weaponNpcSelectedColumn;
  @FXML
  private TextField weaponNpcSelectedRow;
  @FXML
  private TextField weaponNpcColumns;
  @FXML
  private TextField weaponNpcRows;
  
  public WeaponNpc() {}
  
  @FXML
  public void weaponNpcChooseImage() {
    File selectedFile = getFileChooser().showOpenDialog(this.weaponNpc.getScene().getWindow());
    
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
    return this.weaponNpcLabel.getText();
  }
  
  @Override
  public String getGroupId() {
    return this.weaponNpc.getId();
  }
  
  @Override
  public void toggleGridTextFields(boolean value) {
    this.weaponNpcRows.setDisable(!value);
    this.weaponNpcColumns.setDisable(!value);
    this.weaponNpcSelectedRow.setDisable(!value);
    this.weaponNpcSelectedColumn.setDisable(!value);
    
    final float opacityValue = value ? 1.0f : 0.0f;
  
    this.weaponNpcRows.setOpacity(opacityValue);
    this.weaponNpcColumns.setOpacity(opacityValue);
    this.weaponNpcSelectedRow.setOpacity(opacityValue);
    this.weaponNpcSelectedColumn.setOpacity(opacityValue);
  }
  
  @Override
  public void handleRows() {
    this.weaponNpcRows.textProperty().addListener((observable, oldValue, newValue) -> setRows(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleColumns() {
    this.weaponNpcColumns.textProperty().addListener((observable, oldValue, newValue) -> setColumns(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleSelectedRow() {
    this.weaponNpcSelectedRow.textProperty().addListener((observable, oldValue, newValue) -> setSelectedRow(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleSelectedColumn() {
    this.weaponNpcSelectedColumn.textProperty().addListener((observable, oldValue, newValue) -> setSelectedColumn(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void setRows(String value) {
    this.weaponNpcRows.setText(value);
  }
  
  @Override
  public void setColumns(String value) {
    this.weaponNpcColumns.setText(value);
  }
  
  @Override
  public void setSelectedRow(String value) {
    this.weaponNpcSelectedRow.setText(value);
  }
  
  @Override
  public void setSelectedColumn(String value) {
    this.weaponNpcSelectedColumn.setText(value);
  }
  
  @Override
  public Integer getRows() {
    return getNumericValue(this.weaponNpcRows.getText());
  }
  
  @Override
  public Integer getColumns() {
    return getNumericValue(this.weaponNpcColumns.getText());
  }
  
  @Override
  public Integer getSelectedRow() {
    return getNumericValue(this.weaponNpcSelectedRow.getText());
  }
  
  @Override
  public Integer getSelectedColumn() {
    return getNumericValue(this.weaponNpcSelectedColumn.getText());
  }
  
  @Override
  public void setTypeImage(CustomizableTypeImage typeImage) {
    this.current = typeImage;
  }

  @Override
  public CustomizableTypeImage getTypeImage() {
    return this.current;
  }
  
  @Override
  public void setImage(Image image) {
    this.weaponNpcImage.setImage(image);
  }
  
  @Override
  public void setFilename(String filename) {
    this.weaponNpcFilename.setText(filename);
  }
  
  @Override
  public String getFilename() {
    return this.weaponNpcFilename.getText();
  }
  
  @Override
  public void setDefaults() {
    final GridImage grid = getTypeImage().getGrid();
  
    this.weaponNpcFilename.setText(getTypeImage().getName());
    this.weaponNpcImage.setImage(new Image(getTypeImage().getName()));
  
    this.weaponNpcRows.setText(String.valueOf(grid.getRows()));
    this.weaponNpcColumns.setText(String.valueOf(grid.getColumns()));
    this.weaponNpcSelectedRow.setText(String.valueOf(grid.getSelectedRow()));
    this.weaponNpcSelectedColumn.setText(String.valueOf(grid.getSelectedColumn()));
  }
  
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    this.weaponNpcIsDivided.selectedProperty().addListener((observable, oldValue, newValue) -> toggleGridTextFields(observable.getValue()));
    
    handleRows();
    handleColumns();
    handleSelectedRow();
    handleSelectedColumn();
  }
}
