package space.cstm.views;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import space.cstm.controllers.RankingController;

public class RankingView implements View, Initializable {
    private static final float BOUND_FACTOR = 2.0f;
    private static final RankingController controller = new RankingController(RankingController.load());
    
    private Stage stage;

    @FXML
    private VBox rankingVBox;

    @FXML
    private AnchorPane parent;

    public RankingView() {
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
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @Override
    public Stage getStage() {
        return this.stage;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // empty because read-only
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, Float> rankingList = controller.getModel().getRankingList();

        controller.write();

        if (rankingList !=  null) {
            final Text playerNameTitle = new Text("Player name");
            final Text gamePointTitle = new Text("Game Points");
            final HBox hBoxTitle = new HBox();

            hBoxTitle.setAlignment(Pos.CENTER);
            hBoxTitle.maxHeight(200);
            hBoxTitle.maxWidth(600);
            hBoxTitle.minHeight(100);
            hBoxTitle.minWidth(350);
            hBoxTitle.setSpacing(4);

            playerNameTitle.setStyle("-fx-alignment: center; -fx-font-size: 22;");
            gamePointTitle.setStyle("-fx-alignment: center; -fx-font-size: 22;");

            hBoxTitle.getChildren().addAll(playerNameTitle, gamePointTitle);

            this.rankingVBox.getChildren().addAll(hBoxTitle);

            rankingList.forEach((key, value) -> {
                final Text playerName = new Text(key);
                final Text gamePoints = new Text(String.valueOf(value));
                final HBox hBox = new HBox();

                hBox.setAlignment(Pos.CENTER);
                hBox.maxHeight(200);
                hBox.maxWidth(600);
                hBox.minHeight(100);
                hBox.minWidth(350);
                hBox.setSpacing(4);

                playerName.setStyle("-fx-text-alignment: left;-fx-font-size: 20;");
                gamePoints.setStyle("-fx-text-alignment: right;-fx-font-size: 20;");

                hBox.getChildren().addAll(playerName, gamePoints);

                this.rankingVBox.getChildren().add(hBox);
            });
        } else  {
            final Text text = new Text("No one has played");

            text.setStyle("-fx-alignment: center; -fx-font-size: 24;");

            this.rankingVBox.getChildren().add(text);
        }
    }
}

