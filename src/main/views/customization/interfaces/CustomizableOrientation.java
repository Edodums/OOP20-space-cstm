package main.views.customization.interfaces;

import main.utils.enums.Orientations;

public interface CustomizableOrientation extends Customizable {
  
  void handleSelectOrientation();
  
  void setSelectedOrientation(int index);
  
  void setOrientationValue(Orientations value);
  
  Orientations getSelectedOrientation();
  
}
