package main.views.customization.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.exceptions.DirectoryNotCreated;
import main.models.settings.Grid;
import main.models.settings.TypeImage;
import main.services.FileService;
import main.utils.enums.ResourcePath;
import main.views.customization.interfaces.CustomizableTypeImage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EntityCommonShip implements CustomizableTypeImage, Initializable {
  private TypeImage current;
  
  @FXML
  private TextField entityCommonShipFilename;
  @FXML
  private Group entityCommonShip;
  @FXML
  private Label entityCommonShipLabel;
  @FXML
  private CheckBox entityCommonShipIsDivided;
  @FXML
  private ImageView entityCommonShipImage;
  @FXML
  private TextField entityCommonShipSelectedColumn;
  @FXML
  private TextField entityCommonShipSelectedRow;
  @FXML
  private TextField entityCommonShipColumns;
  @FXML
  private TextField entityCommonShipRows;
  
  public EntityCommonShip() {}
  
  @FXML
  public void entityCommonShipChooseImage() {
    File selectedFile = getFileChooser().showOpenDialog(this.entityCommonShip.getScene().getWindow());
    
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
    return this.entityCommonShipLabel.getText();
  }
  
  @Override
  public String getGroupId() {
    return this.entityCommonShip.getId();
  }
  
  @Override
  public void toggleGridTextFields(boolean value) {
    this.entityCommonShipRows.setDisable(!value);
    this.entityCommonShipColumns.setDisable(!value);
    this.entityCommonShipSelectedRow.setDisable(!value);
    this.entityCommonShipSelectedColumn.setDisable(!value);
    
    final float opacityValue = value ? 1.0f : 0.0f;
    
    this.entityCommonShipRows.setOpacity(opacityValue);
    this.entityCommonShipColumns.setOpacity(opacityValue);
    this.entityCommonShipSelectedRow.setOpacity(opacityValue);
    this.entityCommonShipSelectedColumn.setOpacity(opacityValue);
  }
  
  @Override
  public void handleRows() {
    this.entityCommonShipRows.textProperty().addListener((observable, oldValue, newValue) -> setRows(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleColumns() {
    this.entityCommonShipColumns.textProperty().addListener((observable, oldValue, newValue) -> setColumns(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleSelectedRow() {
    this.entityCommonShipSelectedRow.textProperty().addListener((observable, oldValue, newValue) -> setSelectedRow(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleSelectedColumn() {
    this.entityCommonShipSelectedColumn.textProperty().addListener((observable, oldValue, newValue) -> setSelectedColumn(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void setRows(String value) {
    this.entityCommonShipRows.setText(value);
  }
  
  @Override
  public void setColumns(String value) {
    this.entityCommonShipColumns.setText(value);
  }
  
  @Override
  public void setSelectedRow(String value) {
    this.entityCommonShipSelectedRow.setText(value);
  }
  
  @Override
  public void setSelectedColumn(String value) {
    this.entityCommonShipSelectedColumn.setText(value);
  }
  
  @Override
  public Integer getRows() {
    return getNumericValue(this.entityCommonShipRows.getText());
  }
  
  @Override
  public Integer getColumns() {
    return getNumericValue(this.entityCommonShipColumns.getText());
  }
  
  @Override
  public Integer getSelectedRow() {
    return getNumericValue(this.entityCommonShipSelectedRow.getText());
  }
  
  @Override
  public Integer getSelectedColumn() {
    return getNumericValue(this.entityCommonShipSelectedColumn.getText());
  }
  
  @Override
  public void setTypeImage(TypeImage typeImage) {
    this.current = typeImage;
  }
  
  @Override
  public TypeImage getTypeImage() {
    return this.current;
  }
  
  @Override
  public void setImage(Image image) {
    this.entityCommonShipImage.setImage(image);
  }
  
  @Override
  public void setFilename(String filename) {
    this.entityCommonShipFilename.setText(filename);
  }
  
  @Override
  public String getFilename() {
    return this.entityCommonShipFilename.getText();
  }
  
  @Override
  public void setDefaults() {
    final Grid grid = getTypeImage().getGrid();
    
    this.entityCommonShipFilename.setText(getTypeImage().getName());
    this.entityCommonShipImage.setImage(new Image(getTypeImage().getName()));
    
    this.entityCommonShipRows.setText(String.valueOf(grid.getRows()));
    this.entityCommonShipColumns.setText(String.valueOf(grid.getColumns()));
    this.entityCommonShipSelectedRow.setText(String.valueOf(grid.getSelectedRow()));
    this.entityCommonShipSelectedColumn.setText(String.valueOf(grid.getSelectedColumn()));
  }
  
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    this.entityCommonShipIsDivided.selectedProperty().addListener((observable, oldValue, newValue) -> toggleGridTextFields(observable.getValue()));
    
    handleRows();
    handleColumns();
    handleSelectedRow();
    handleSelectedColumn();
  }
}
