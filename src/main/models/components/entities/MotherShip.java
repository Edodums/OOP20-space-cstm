package main.models.components.entities;

import java.util.Map;
import java.util.Optional;
import main.models.components.Collider;
import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Entity;
import main.models.settings.TypeImage;
import main.utils.Pair;


public class MotherShip extends Collider implements Entity, Collidable {

    private static final double POINTS = 100;
    private static final double SPAWN_NUMBER = 1;
    private static final double WIDTH = 4.0;
    private static final double HEIGHT = 2.5;
    private static final Pair<Double,Double> STARTING_POINT = new Pair<>(0.0, 0.0);

    private final TypeImage typeImage;

    public MotherShip(TypeImage typeImage) {
        super();
        this.typeImage = typeImage;
    }

    @Override
    public Map<Pair<Double, Double>, Optional<Entity>> create() {
        return Map.of(STARTING_POINT, Optional.of(this));
    }

    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }

    @Override
    public Pair<Double, Double> getPosition() {
        return STARTING_POINT;
    }

    @Override
    public void fire() {
        //empty
    }

    @Override
    public void die() {System.out.println("Mothership is dead!");}
  
    @Override
    public TypeImage getTypeImages() {
      return this.typeImage;
    }
  
    @Override
    public double getPointsValue() {
        return POINTS;
    }

    @Override
    public double getSpawnNumber() {
        return SPAWN_NUMBER ;
    }

    public Pair<Double, Double> getStartingPoint() {
        return STARTING_POINT;
    }
}

