package main.views;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.controllers.SettingsController;
import main.models.settings.Grid;
import main.models.settings.TypeImage;
import main.models.settings.interfaces.Type;
import main.models.settings.interfaces.CustomizableTypeImage;
import main.utils.enums.EntityType;
import main.utils.enums.WeaponType;
import main.views.customization.components.CustomizableComponentFactory;
import main.views.customization.interfaces.CustomizableView;
import main.views.customization.interfaces.CustomizableViewOrientation;
import main.views.customization.interfaces.CustomizableViewTypeImage;

/**
 * The view part of settings
 */
public class SettingsView implements View, Initializable {
    private static final float BOUND_FACTOR = 1.4f;
    private static final SettingsController controller = new SettingsController(SettingsController.load());
    
    private Stage stage;
    
    @FXML
    private AnchorPane parent;
    
    public SettingsView() {
        addListenerToModel(controller.getModel());
    }
    
    @Override
    public Pane getParent() {
        return this.parent;
    }

    @Override
    public float getBoundFactor() {
        return BOUND_FACTOR;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // empty
    }
    
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @Override
    public Stage getStage() {
        return this.stage;
    }
    /**
     * call the interface method , setDefaults
     * @param event
     */
    @FXML
    public void cancelSettings(ActionEvent event) {
        CustomizableComponentFactory.components.forEach(CustomizableView::setDefaults);
    }
    /**
     *
     * @param event
     */
    @FXML
    public void saveSettings(ActionEvent event) {
        CustomizableComponentFactory
              .components
              .forEach(customizable -> {
                  if (customizable instanceof CustomizableViewOrientation) {
                      controller.getModel().setOrientation(((CustomizableViewOrientation) customizable).getSelectedOrientation());
                  }
                  
                  if (customizable instanceof CustomizableViewTypeImage) {
                      final Type type;
                      final CustomizableViewTypeImage component =  ((CustomizableViewTypeImage) customizable);
                      final Grid grid = component.getRows() == 0 ? new Grid() : new Grid(component.getRows(), component.getColumns(), component.getSelectedRow(), component.getSelectedColumn());
                      
                      if (component.getGroupId().contains("weapon"))  {
                          type = WeaponType.getType(component.getGroupId());
                      } else {
                          type = EntityType.getType(component.getGroupId());
                      }
                      
                      final CustomizableTypeImage typeImage = new TypeImage(component.getFilename(), type, grid);
                      
                      controller.getModel().replaceTypeImage(component.getGroupId(), typeImage);
                  }
              });
        
        controller.write();
    }
    /**
     * Add a parent the components through the factory (static factory method) which makes use of static methods
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getParent().getChildren().addAll(CustomizableComponentFactory.getCustomizableComponents(controller.getModel()));
    }
}
