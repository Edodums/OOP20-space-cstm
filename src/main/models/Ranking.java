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
    private final Map<String, Float> rankingList = new HashMap<>();

    public Ranking() {
        // empty
    }

    public Map<String, Float> getRankingList(){
        return this.rankingList;
    }

    public void addToRankingList(final String playerName, final Float gamePoints) {
        Map<String, Float> oldMap = new HashMap<>(this.rankingList);

        this.rankingList.put(playerName, gamePoints);

        firePropertyChange("rankingList", oldMap, this.rankingList);
    }

    @Override
    public PropertyChangeSupport getSupport() {
        return this.support;
    }
}

