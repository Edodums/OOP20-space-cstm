package space.cstm.models.components.entities;

import java.util.Map;
import java.util.Optional;
import space.cstm.events.MotherShipHitEvent;
import space.cstm.models.components.Collider;
import space.cstm.models.components.interfaces.Collidable;
import space.cstm.models.components.interfaces.Entity;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.utils.Pair;
import org.greenrobot.eventbus.EventBus;


public class MotherShip extends Collider implements Entity, Collidable {

    private static final float POINTS = 100;
    private static final float SPAWN_NUMBER = 1;
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 0.75f;
    private static final Pair<Float, Float> STARTING_POINT = new Pair<>(0.0f, 0.0f);

    private final CustomizableTypeImage typeImage;

    public MotherShip(CustomizableTypeImage typeImage) {
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
    public void die(Collider entityToCheck) {
        EventBus.getDefault().post(new MotherShipHitEvent((MotherShip) entityToCheck));
    }
  
    @Override
    public CustomizableTypeImage getTypeImages() {
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

