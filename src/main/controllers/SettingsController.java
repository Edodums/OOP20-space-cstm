package main.controllers;


import main.models.ObservableModel;
import main.models.Settings;
import main.services.YamlService;
import main.views.View;



public class SettingsController implements Controller {

    private final YamlService yamlService = new YamlService();
    private Settings model;

    public SettingsController(){
        setModel(new Settings());
    }


    @Override
    public void setModel(ObservableModel model) {
        this.model = (Settings) model;
    }

    public Settings load() {
        return (Settings) this.yamlService.readFile("settings", model);
    }



    @Override
    public void setView(View view) {

    }
}
