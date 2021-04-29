package main.controllers;

import main.models.ObservableModel;
import main.models.Ranking;
import main.services.YamlService;

public class RankingController implements Controller {

    private static final YamlService yamlService = new YamlService();
    private static Ranking model;

    public RankingController(Ranking ranking) {
        setModel(ranking);
    }

    @Override
    public void setModel(ObservableModel ranking) {
        model = (Ranking) ranking;
    }

    public static Ranking load() {
        return (Ranking) yamlService.readFile("ranking", Ranking.class);
    }

    public void write() {
        yamlService.writeFile("ranking", getModel());
    }
    
    public static void addToRanking(String playerName, float gamePoints) {
        Ranking ranking = load();
        
        ranking.addToRankingList(playerName, gamePoints);
        
        yamlService.writeFile("ranking", ranking);
    }

    public Ranking getModel() {
        return model;
    }
}

