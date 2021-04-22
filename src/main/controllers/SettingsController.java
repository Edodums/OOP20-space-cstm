package main.controllers;


import main.models.ObservableModel;
import main.models.settings.Settings;
import main.services.YamlService;

public class SettingsController implements Controller {

    private static final YamlService yamlService = new YamlService();
    private Settings model;

    public SettingsController(Settings settings) {
        setModel(settings);
        getModel().loadDefault();
    }
    
    @Override
    public void setModel(ObservableModel model) {
        this.model = (Settings) model;
    }

    public static Settings load() {
         return (Settings) yamlService.readFile("settings", Settings.class);
    }
    
    public void write() {
        yamlService.writeFile("settings", getModel());
    }
  
    public Settings getModel() {
        return this.model;
    }
}
