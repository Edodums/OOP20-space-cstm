package main.views.entities;

import main.models.components.interfaces.Entity;
import main.utils.Pair;
import main.views.entities.interfaces.EntitySprite;

import javax.swing.text.html.ImageView;
import java.util.Map;
import java.util.Optional;

public class MotherShipView implements EntitySprite {

    @Override
    public ImageView create(Map<Pair<Double, Double>, Optional<Entity>> entities, double unit) {
        Optional<Entity> entity = entities.entrySet()
                .stream()
                .findFirst()
                .flatMap(Map.Entry::getValue);
        if (entity.isEmpty()) {
            return null;
        }
        return new ImageView(entity.get().getFilename());
    }
}
