package main.controllers;

import main.models.ObservableModel;
import main.models.Ranking;
import main.services.YamlService;

import java.util.Map;

public class RankingController implements Controller {

    private Ranking model;
    private final YamlService yamlService = new YamlService();

    public RankingController() {
        setModel(new Ranking());
    }

    @Override
    public void setModel(ObservableModel model) {
        this.model = (Ranking) model;
    }

    public Map<String, Double> load() {
        Ranking ranking = (Ranking) this.yamlService.readFile("ranking", model);

        return ranking.getRankingList();
    }
}
