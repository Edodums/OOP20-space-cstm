package main.models;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public class Ranking implements ObservableModel{

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Map<String, Double> rankingList = new HashMap<>();

    public Ranking() {
        //empty
    }

    public Map<String, Double> getRankingList(){
        return this.rankingList;
    }

    @Override
    public PropertyChangeSupport getSupport(){
        return this.support;
    }
}
