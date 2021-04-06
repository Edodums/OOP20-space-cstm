package main.views.entities.interfaces;

import javafx.scene.Node;
import main.models.components.interfaces.Entity;
import main.utils.Pair;

import java.util.Map;
import java.util.Optional;

public interface EntitySprite {
    Node create(Map<Pair<Integer, Integer>, Optional<Entity>> entity);
}
