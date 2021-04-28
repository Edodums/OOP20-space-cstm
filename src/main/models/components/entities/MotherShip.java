package main.models.components.entities;

import java.util.Map;
import java.util.Optional;
import main.events.MotherShipHitEvent;
import main.models.components.Collider;
import main.models.components.interfaces.Collidable;
import main.models.components.interfaces.Entity;
import main.models.components.interfaces.Weapon;
import main.models.settings.TypeImage;
import main.utils.Pair;
import org.greenrobot.eventbus.EventBus;


public class MotherShip extends Collider implements Entity, Collidable {

    private static final float POINTS = 100;
    private static final float SPAWN_NUMBER = 1;
    private static final float WIDTH = 4.0f;
    private static final float HEIGHT = 2.5f;
    private static final Pair<Float, Float> STARTING_POINT = new Pair<>(0.0f, 0.0f);

    private final TypeImage typeImage;

    public MotherShip(TypeImage typeImage) {
        super();
        this.typeImage = typeImage;
    }

    @Override
    public Map<Pair<Float, Float>, Optional<Entity>> create() {
        final Pair<Float, Float> startingPosition = STARTING_POINT;
        setStartingPosition(startingPosition);
        
        final Pair<Float, Float> position = new Pair<>(startingPosition.getX(), startingPosition.getY());
        setPosition(position);
        
        return Map.of(position, Optional.of(this));
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public Pair<Float, Float> getPosition() {
        return STARTING_POINT;
    }

    @Override
    public void die(Weapon weapon) {
        EventBus.getDefault().post(new MotherShipHitEvent(this, weapon));
    }
  
    @Override
    public TypeImage getTypeImages() {
      return this.typeImage;
    }
  
    @Override
    public float getPointsValue() {
        return POINTS;
    }

    @Override
    public float getSpawnNumber() {
        return SPAWN_NUMBER ;
    }
}

