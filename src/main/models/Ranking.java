package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public class Ranking implements ObservableModel {
    @JsonIgnore
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    @JsonSerialize
    private Map<String, Double> rankingList = new HashMap<>();

    public Ranking() {
        // empty
    }

    public Map<String, Double> getRankingList(){
        return this.rankingList;
    }

    public void addToRankingList(final String playerName, final Double gamePoints) {
        Map<String, Double> oldMap = this.rankingList;

        this.rankingList.put(playerName, gamePoints);

        firePropertyChange("rankingList", oldMap, this.rankingList);
    }

    @Override
    public PropertyChangeSupport getSupport() {
        return this.support;
    }
}

