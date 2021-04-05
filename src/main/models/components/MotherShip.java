package main.models.components;

import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Entity;
import main.utils.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;

public class MotherShip extends Collider implements Entity, Collidable {


    private static final double COLUMNS = 1;
    private static final double ROWS = 1;
    private static final Pair<Double,Double> STARTING_POINT = new Pair<>(0.0, 0.0);

    public MotherShip(){}

    public void move(){}


    @Override
    public Map<Pair<Double, Double>, Optional<Entity>> create(Object newHashMap) {
        MotherShip motherShip = this ;

        return Collections.unmodifiableMap(new HashMap<>(){{
            put(STARTING_POINT, Optional.of(motherShip)); }});
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
    public Pair<Double, Double> getPosition() { return STARTING_POINT; }

    @Override
    public void fire() { }

    @Override
    public void die() {System.out.println("Mothership is dead!");}

    @Override
    public double getSpawnNumber() { return getMotherShipColumns() * getMotherShipRows(); }

    private double getMotherShipRows() { return ROWS; }

    private double getMotherShipColumns() {return COLUMNS; }

}

