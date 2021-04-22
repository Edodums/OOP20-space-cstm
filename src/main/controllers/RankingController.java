package main.controllers;

import main.models.ObservableModel;
import main.models.Ranking;
import main.services.YamlService;

public class RankingController implements Controller {

    private static final YamlService yamlService = new YamlService();
    private Ranking model;

    public RankingController(Ranking ranking) {
        setModel(ranking);
    }

    @Override
    public void setModel(ObservableModel model) {
        this.model = (Ranking) model;
    }

    public static Ranking load() {
        return (Ranking) yamlService.readFile("ranking", Ranking.class);
    }

    public void write() {
        yamlService.writeFile("ranking", getModel());
    }

    public Ranking getModel() {
        return this.model;
    }
}

