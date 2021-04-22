package main.views.customization.interfaces;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import main.models.settings.TypeImage;

public interface CustomizableTypeImage extends Customizable {
  
  void toggleGridTextFields(boolean value);
  
  Integer getRows();
  
  Integer getColumns();
  
  Integer getSelectedRow();
  
  Integer getSelectedColumn();
  
  void handleRows();
  
  void handleColumns();
  
  void handleSelectedRow();
  
  void handleSelectedColumn();
  
  void setRows(String value);
  
  void setColumns(String value);
  
  void setSelectedRow(String value);
  
  void setSelectedColumn(String value);
  
  void setTypeImage(TypeImage typeImage);
  
  TypeImage getTypeImage();
  
  void setImage(Image image);
  
  void setFilename(String filename);
  
  String getFilename();
  
  default FileChooser getFileChooser() {
    FileChooser fileChooser = new FileChooser();
    
    fileChooser.setTitle("Open Resource File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
    
    return fileChooser;
  }
  
  default String getFormattedNumericValue(String value) {
    String numericValue = value;
    
    if (!numericValue.matches("\\d*")) {
      numericValue = numericValue.replaceAll("[^\\d]","");
    }
    
    return numericValue;
  }
  
  default Integer getNumericValue(String value) {
    return value.trim().isEmpty() ? 0 : Integer.parseInt(value);
  }
  
}
