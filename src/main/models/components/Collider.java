package main.models.components;

import main.utils.Pair;

public abstract class Collider {
    private Pair<Double, Double> position;

    public abstract double getWidth();
    public abstract double getHeight();
    public abstract Pair<Double, Double> getStartingPoint();

    public Pair<Double, Double> getPosition()  {
        return this.position;
    }

    public void setPosition(Pair<Double, Double> currentPosition) {
        this.position = currentPosition;
    }
}
