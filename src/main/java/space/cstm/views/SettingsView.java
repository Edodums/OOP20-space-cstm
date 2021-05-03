package space.cstm.views;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import space.cstm.controllers.SettingsController;
import space.cstm.models.settings.Grid;
import space.cstm.models.settings.TypeImage;
import space.cstm.models.settings.interfaces.Type;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.utils.enums.CurrentScene;
import space.cstm.utils.enums.EntityType;
import space.cstm.utils.enums.WeaponType;
import space.cstm.views.customization.components.CustomizableComponentFactory;
import space.cstm.views.customization.interfaces.CustomizableView;
import space.cstm.views.customization.interfaces.CustomizableViewOrientation;
import space.cstm.views.customization.interfaces.CustomizableViewTypeImage;

/**
 * The view part of settings
 */
public class SettingsView implements View, Initializable {
    private static final float BOUND_FACTOR = 1.4f;
    private static final SettingsController controller = new SettingsController(SettingsController.load());
    
    private Stage stage;
    
    @FXML
    private AnchorPane parent;

    @FXML
    private ScrollPane scrollPane;
    
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

    @FXML
    public void goBack(ActionEvent actionEvent) {
        MenuView.goToScene(CurrentScene.MENU);
    }

    /**
     * Add a parent the components through the factory (static factory method) which makes use of static methods
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPadding(new Insets(18.0));

        final Set<Group> groups = CustomizableComponentFactory.getCustomizableComponents(controller.getModel());
        final Group group = new Group();

        group.getChildren().addAll(groups);

        scrollPane.setContent(group);
    }
}
