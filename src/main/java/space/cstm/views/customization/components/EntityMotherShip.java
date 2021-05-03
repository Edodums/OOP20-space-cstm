package space.cstm.views.customization.components;

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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EntityMotherShip implements CustomizableViewTypeImage, Initializable {
  private CustomizableTypeImage current;
  
  @FXML
  private TextField entityMotherShipFilename;
  @FXML
  private Group entityMotherShip;
  @FXML
  private Label entityMotherShipLabel;
  @FXML
  private CheckBox entityMotherShipIsDivided;
  @FXML
  private ImageView entityMotherShipImage;
  @FXML
  private TextField entityMotherShipSelectedColumn;
  @FXML
  private TextField entityMotherShipSelectedRow;
  @FXML
  private TextField entityMotherShipColumns;
  @FXML
  private TextField entityMotherShipRows;
  
  public EntityMotherShip() {}
  
  @FXML
  public void entityMotherShipChooseImage() {
    File selectedFile = getFileChooser().showOpenDialog(this.entityMotherShip.getScene().getWindow());
    
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
    return this.entityMotherShipLabel.getText();
  }
  
  @Override
  public String getGroupId() {
    return this.entityMotherShip.getId();
  }
  
  @Override
  public void toggleGridTextFields(boolean value) {
    this.entityMotherShipRows.setDisable(!value);
    this.entityMotherShipColumns.setDisable(!value);
    this.entityMotherShipSelectedRow.setDisable(!value);
    this.entityMotherShipSelectedColumn.setDisable(!value);
    
    final float opacityValue = value ? 1.0f : 0.0f;
    
    this.entityMotherShipRows.setOpacity(opacityValue);
    this.entityMotherShipColumns.setOpacity(opacityValue);
    this.entityMotherShipSelectedRow.setOpacity(opacityValue);
    this.entityMotherShipSelectedColumn.setOpacity(opacityValue);
  }
  
  @Override
  public void handleRows() {
    this.entityMotherShipRows.textProperty().addListener((observable, oldValue, newValue) -> setRows(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleColumns() {
    this.entityMotherShipColumns.textProperty().addListener((observable, oldValue, newValue) -> setColumns(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleSelectedRow() {
    this.entityMotherShipSelectedRow.textProperty().addListener((observable, oldValue, newValue) -> setSelectedRow(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void handleSelectedColumn() {
    this.entityMotherShipSelectedColumn.textProperty().addListener((observable, oldValue, newValue) -> setSelectedColumn(getFormattedNumericValue(newValue)));
  }
  
  @Override
  public void setRows(String value) {
    this.entityMotherShipRows.setText(value);
  }
  
  @Override
  public void setColumns(String value) {
    this.entityMotherShipColumns.setText(value);
  }
  
  @Override
  public void setSelectedRow(String value) {
    this.entityMotherShipSelectedRow.setText(value);
  }
  
  @Override
  public void setSelectedColumn(String value) {
    this.entityMotherShipSelectedColumn.setText(value);
  }
  
  @Override
  public Integer getRows() {
    return getNumericValue(this.entityMotherShipRows.getText());
  }
  
  @Override
  public Integer getColumns() {
    return getNumericValue(this.entityMotherShipColumns.getText());
  }
  
  @Override
  public Integer getSelectedRow() {
    return getNumericValue(this.entityMotherShipSelectedRow.getText());
  }
  
  @Override
  public Integer getSelectedColumn() {
    return getNumericValue(this.entityMotherShipSelectedColumn.getText());
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
    this.entityMotherShipImage.setImage(image);
  }
  
  @Override
  public void setFilename(String filename) {
    this.entityMotherShipFilename.setText(filename);
  }
  
  @Override
  public String getFilename() {
    return this.entityMotherShipFilename.getText();
  }
  
  @Override
  public void setDefaults() {
    final GridImage grid = getTypeImage().getGrid();
    
    this.entityMotherShipFilename.setText(getTypeImage().getName());
    this.entityMotherShipImage.setImage(new Image(getTypeImage().getName()));
    
    this.entityMotherShipRows.setText(String.valueOf(grid.getRows()));
    this.entityMotherShipColumns.setText(String.valueOf(grid.getColumns()));
    this.entityMotherShipSelectedRow.setText(String.valueOf(grid.getSelectedRow()));
    this.entityMotherShipSelectedColumn.setText(String.valueOf(grid.getSelectedColumn()));
  }
  
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    this.entityMotherShipIsDivided.selectedProperty().addListener((observable, oldValue, newValue) -> toggleGridTextFields(observable.getValue()));
    
    handleRows();
    handleColumns();
    handleSelectedRow();
    handleSelectedColumn();
  }
}
