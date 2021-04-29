package main.views.customization.interfaces;

import main.utils.enums.Orientations;

public interface CustomizableViewOrientation extends CustomizableView {
  
  void handleSelectOrientation();
  
  void setSelectedOrientation(int index);
  
  void setOrientationValue(Orientations value);
  
  Orientations getSelectedOrientation();
  
}
