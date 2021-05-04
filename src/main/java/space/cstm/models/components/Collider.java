package space.cstm.models.components;

import space.cstm.utils.Pair;

public abstract class Collider {
    private Pair<Float, Float> position;
    private Pair<Float, Float> startingPosition;
    
    public abstract float getWidth();
    
    public abstract float getHeight();
    
    public Pair<Float, Float> getStartingPoint() {
        return this.startingPosition;
    }
    
    public void setStartingPosition(final Pair<Float, Float> position)  {
        this.startingPosition = position;
    }
    
    public Pair<Float, Float> getPosition()  {
        return this.position;
    }

    public void setPosition(final Pair<Float, Float> currentPosition) {
        this.position = currentPosition;
    }
}
