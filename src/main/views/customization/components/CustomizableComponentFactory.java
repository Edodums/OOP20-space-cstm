package main.views.customization.components;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import main.models.settings.Settings;
import main.models.settings.TypeImage;
import main.services.FileService;
import main.utils.enums.Orientations;
import main.utils.enums.ResourcePath;
import main.views.customization.interfaces.Customizable;
import main.views.customization.interfaces.CustomizableOrientation;
import main.views.customization.interfaces.CustomizableTypeImage;

public class CustomizableComponentFactory {
  public static final Set<Customizable> components = new HashSet<>();
  private static final Set<File> files = FileService.getAllFilesInDirectory(ResourcePath.SETTINGS_COMPONENTS.toString());
  
  private CustomizableComponentFactory() {}
  
  public static Set<Group> getCustomizableComponents(final Settings settings) {
    final Orientations orientation = settings.getOrientation();
    final Map<String, TypeImage> typeImages = settings.getTypeImages();
    
    Set<Group> customizableSet = new HashSet<>();
  
    files.forEach(file -> {
      try {
        final FXMLLoader loader = new FXMLLoader(file.toURI().toURL());
        final Group group = loader.load();
        final Customizable component = loader.getController();
        
        if (component instanceof CustomizableOrientation)  {
           ((CustomizableOrientation) component).setOrientationValue(orientation);
        }
        
        if (component instanceof CustomizableTypeImage)  {
          final TypeImage typeImage = typeImages.get(group.getId());
          ((CustomizableTypeImage) component).setTypeImage(typeImage);
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
