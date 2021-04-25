package main.views;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.controllers.SettingsController;
import main.models.settings.Grid;
import main.models.settings.Type;
import main.models.settings.TypeImage;
import main.utils.enums.EntityType;
import main.utils.enums.WeaponType;
import main.views.customization.components.CustomizableComponentFactory;
import main.views.customization.interfaces.Customizable;
import main.views.customization.interfaces.CustomizableOrientation;
import main.views.customization.interfaces.CustomizableTypeImage;

/**
 * The view part of settings
 */
public class SettingsView implements View, Initializable {
    private static final double BOUND_FACTOR = 1.4;
    private static final SettingsController controller = new SettingsController(SettingsController.load());
    
    private Stage stage;
    
    @FXML
    private AnchorPane parent;
    
    public SettingsView() {
        controller.getModel().addPropertyChangeListener(this);
    }
    
    @Override
    public Pane getParent() {
        return this.parent;
    }

    @Override
    public double getBoundFactor() {
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
        CustomizableComponentFactory.components.forEach(Customizable::setDefaults);
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
                  if (customizable instanceof CustomizableOrientation) {
                      controller.getModel().setOrientation(((CustomizableOrientation) customizable).getSelectedOrientation());
                  }
                  
                  if (customizable instanceof CustomizableTypeImage) {
                      final Type type;
                      final CustomizableTypeImage component =  ((CustomizableTypeImage) customizable);
                      final Grid grid = component.getRows() == 0 ? new Grid() : new Grid(component.getRows(), component.getColumns(), component.getSelectedRow(), component.getSelectedColumn());
                      
                      if (component.getGroupId().contains("weapon"))  {
                          type = WeaponType.getType(component.getGroupId());
                      } else {
                          type = EntityType.getType(component.getGroupId());
                      }
                      
                      final TypeImage typeImage = new TypeImage(component.getFilename(), type, grid);
                      
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

        ScrollPane scrollPane = new ScrollPane();
        //Rectangle rectangle = new Rectangle(800,600);
        scrollPane.setPrefSize(600, 400);
        //scrollPane.setContent(rectangle);
        CustomizableComponentFactory.getCustomizableComponents(controller.getModel()).forEach(scrollPane::setContent);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.pannableProperty().set(true);
        getParent().getChildren().add(scrollPane);

        //getParent().getChildren().addAll(CustomizableComponentFactory.getCustomizableComponents(controller.getModel()));
    }
}
