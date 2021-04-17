package main.views;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import main.controllers.RankingController;
import main.models.Ranking;

import java.beans.PropertyChangeEvent;

public class RankingView implements View{
    private static final double BOUND_FACTOR = 2.0;
    private GridPane parent;
    private final Ranking model;
    private final RankingController controller;


    public RankingView(Ranking model, RankingController controller){
        this.model = model;
        this.controller = controller;

        this.controller.load().forEach((key, value) -> {
            Text playerName = new Text(key);
            Text gamePoints = new Text(String.valueOf(value));
            Group group = new Group();

            group.getChildren().add(playerName);
            group.getChildren().add(gamePoints);

            parent.getChildren().add(group);
        })
    }
    @Override
    public Parent getParent() {
        return this.parent;
    }

    @Override
    public double getBoundFactor() {
        return BOUND_FACTOR;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // empty because read-only
    }
}
