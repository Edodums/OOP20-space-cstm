package main.views.customization.components;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import main.models.settings.Settings;
import main.models.settings.interfaces.CustomizableTypeImage;
import main.models.settings.interfaces.Orientable;
import main.services.FileService;
import main.utils.enums.ResourcePath;
import main.views.customization.interfaces.CustomizableView;
import main.views.customization.interfaces.CustomizableViewOrientation;
import main.views.customization.interfaces.CustomizableViewTypeImage;

public class CustomizableComponentFactory {
  public static final Set<CustomizableView> components = new HashSet<>();
  private static final Set<File> files = FileService.getAllFilesInDirectory(ResourcePath.SETTINGS_COMPONENTS.toString());
  
  private CustomizableComponentFactory() {}
  
  public static Set<Group> getCustomizableComponents(final Settings settings) {
    final Orientable orientation = settings.getOrientation();
    final Map<String, CustomizableTypeImage> typeImages = settings.getTypeImages();
    
    Set<Group> customizableSet = new HashSet<>();
  
    files.forEach(file -> {
      try {
        final FXMLLoader loader = new FXMLLoader(file.toURI().toURL());
        final Group group = loader.load();
        final CustomizableView component = loader.getController();
        
        if (component instanceof CustomizableViewOrientation)  {
           ((CustomizableViewOrientation) component).setOrientationValue(orientation);
        }
        
        if (component instanceof CustomizableViewTypeImage)  {
          final CustomizableTypeImage typeImage = typeImages.get(group.getId());
          ((CustomizableViewTypeImage) component).setTypeImage(typeImage);
        }
  
        component.setDefaults();
        components.add(component);
        customizableSet.add(group);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  
    return customizableSet;
  }
}
