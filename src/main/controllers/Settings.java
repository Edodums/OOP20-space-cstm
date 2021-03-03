package main.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import main.utils.Fxml;

public class Settings extends Controller{
    public Settings(){}

    public Settings (Stage stage){
        String filename = Fxml.SETTINGS.toString();
        setParent(filename);
        setScene();

        stage.setScene(getScene());
        stage.show();
    }



}
