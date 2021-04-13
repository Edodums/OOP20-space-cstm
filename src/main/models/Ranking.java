package main.models;

import java.util.HashMap;
import java.util.Map;

public class Ranking extends ObservableModel{

    private Map<String, Double> rankingList = new HashMap<>();

    public Ranking() {}

    public Map<String, Double> getRankingList(){
        return this.rankingList;
    }
}
