package space.cstm.views.customization.components;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import space.cstm.models.settings.Settings;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.models.settings.interfaces.Orientable;
import space.cstm.services.FileService;
import space.cstm.services.JarService;
import space.cstm.utils.enums.ResourcePath;
import space.cstm.views.customization.interfaces.CustomizableView;
import space.cstm.views.customization.interfaces.CustomizableViewOrientation;
import space.cstm.views.customization.interfaces.CustomizableViewTypeImage;

public class CustomizableComponentFactory {
  public static final Set<CustomizableView> components = new HashSet<>();
  private static Set<String> files;
  
  private CustomizableComponentFactory() {}
  
  public static Set<Group> getCustomizableComponents(final Settings settings) {
    final Orientable orientation = settings.getOrientation();
    final Map<String, CustomizableTypeImage> typeImages = settings.getTypeImages();
    
    Set<Group> customizableSet = new HashSet<>();

    String directoryPath = ResourcePath.SETTINGS_COMPONENTS.toString();

    if (JarService.runningFromJar()) {
      directoryPath = ResourcePath.JAR_SETTINGS_COMPONENTS.toString();
    }

    files = FileService.getAllFilesInDirectory(directoryPath);
  
    files.forEach(file -> {
      try {
        final FXMLLoader loader = new FXMLLoader(CustomizableComponentFactory.class.getResource(file));
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
