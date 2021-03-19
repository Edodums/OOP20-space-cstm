package main.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import main.controllers.Ranking;
import main.utils.Fxml;

public class MenuView extends View {

    public MenuView() {}

    public MenuView(Stage stage) {
        String filename = Fxml.MENU.toString();
        setParent(filename);
        setScene();

        stage.setScene(getScene());
        stage.show();
    }

    @FXML
    public void start(ActionEvent actionEvent) {
         changeScene(Fxml.GAME.toString());

         Stage stage = getUpdatedStage(actionEvent);

        // new Game(stage);
    }

    @FXML
    public void settings(ActionEvent actionEvent) {
        changeScene(Fxml.SETTINGS.toString());

        Stage stage = getUpdatedStage(actionEvent);

        //new Settings(stage);
    }

    @FXML
    public void ranking(ActionEvent actionEvent){
        changeScene(Fxml.RANKING.toString());

        Stage stage = getUpdatedStage(actionEvent);

        new Ranking(stage);
    }
    private void changeScene(String filename) {
        setParent(filename);
        setScene();

    }

    private Stage getUpdatedStage(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(getScene());
        stage.show();

        return stage;
    }

}
