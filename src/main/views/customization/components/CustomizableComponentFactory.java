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

/**
 * A static Factory methods class for fxml components binding with the class that implements customizable.
 */
public class CustomizableComponentFactory {
  public static final Set<Customizable> components = new HashSet<>();
  private static final Set<File> files = FileService.getAllFilesInDirectory(ResourcePath.SETTINGS_COMPONENTS.toString());
  
  private CustomizableComponentFactory() {}

  /**
   * This method get all the files in the directory.
   * @param settings
   * @return
   */
  public static Set<Group> getCustomizableComponents(final Settings settings) {
    final Orientations orientation = settings.getOrientation();
    final Map<String, TypeImage> typeImages = settings.getTypeImages();
    
    Set<Group> customizableSet = new HashSet<>();
  
    files.forEach(file -> {
      try {
        final FXMLLoader loader = new FXMLLoader(file.toURI().toURL()); // Get files with FXMLLoader
        final Group group = loader.load(); // load files in group
        final Customizable component = loader.getController(); // get the controller from fx:controller in the file fxml. GetController call also the method Initialize of the components
        
        if (component instanceof CustomizableOrientation)  {
           ((CustomizableOrientation) component).setOrientationValue(orientation);
        }
        
        if (component instanceof CustomizableTypeImage)  {
          final TypeImage typeImage = typeImages.get(group.getId());
          ((CustomizableTypeImage) component).setTypeImage(typeImage);
        }
  
        component.setDefaults(); // set default for each file
        components.add(component);
        customizableSet.add(group); // add the group in the file to customizableSet
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  
    return customizableSet; // Add customizableSet (that is a set of the group) to parent that is in Settings.
  }
}
