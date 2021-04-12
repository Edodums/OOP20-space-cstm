package main.views;

import javafx.scene.Parent;
import javafx.stage.Stage;
import main.models.Settings;
import main.utils.enums.EntityType;
import main.utils.enums.Orientations;
import main.utils.enums.Fxml;
import main.controllers.SettingsController;

import java.beans.PropertyChangeEvent;


public class SettingsView implements View{
    private Settings model;
    private SettingsController controller;
    private Stage stage;

    public SettingsView(Settings model, SettingsController controller, Stage stage){
        this.model = model;
        this.controller = controller;
        this.model.addPropertyChangeListener(this);
        this.stage = stage;
    }


    @Override
    public Parent getParent() {
        return null;
    }

    @Override
    public double getBoundFactor() {
        return 0;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }



}
