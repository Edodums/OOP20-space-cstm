package main.models.components;

import main.utils.Pair;

public abstract class Collider {
    private Pair<Integer, Integer> position;

    public abstract double getWidth();
    public abstract double getHeight();

    public Pair<Integer, Integer> getPosition()  {
        return this.position;
    }

    public void setPosition(Pair<Integer, Integer> currentPosition) {
        this.position = currentPosition;
    }
}
