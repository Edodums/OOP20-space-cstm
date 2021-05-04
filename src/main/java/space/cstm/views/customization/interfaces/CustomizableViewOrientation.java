package space.cstm.views.customization.interfaces;

import space.cstm.models.settings.interfaces.Orientable;

public interface CustomizableViewOrientation extends CustomizableView {
  
  void handleSelectOrientation();
  
  void setSelectedOrientation(int index);
  
  void setOrientationValue(final Orientable value);
  
  Orientable getSelectedOrientation();
  
}
