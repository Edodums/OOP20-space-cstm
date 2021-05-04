package space.cstm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public class Ranking implements ObservableModel {
    @JsonIgnore
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    @JsonSerialize
    private Map<String, Float> rankingList = new HashMap<>();

    public Ranking() {
        // empty
    }

    public Map<String, Float> getRankingList(){
        return this.rankingList;
    }

    public void addToRankingList(final String playerName, final Float gamePoints) {
        if (this.rankingList == null) {
            this.rankingList = new HashMap<>();
        }

        this.rankingList.put(playerName, gamePoints);

        firePropertyChange("rankingList", null, this.rankingList);
    }

    @Override
    public PropertyChangeSupport getSupport() {
        return this.support;
    }
}

