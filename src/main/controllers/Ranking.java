package main.controllers;

import javafx.stage.Stage;
import main.utils.Fxml;

public class Ranking extends Controller{

    public Ranking(){}

    public Ranking(Stage stage) {
        String filename = Fxml.RANKING.toString();
        setParent(filename);
        setScene();

        stage.setScene(getScene());
        stage.show();

    }


}
