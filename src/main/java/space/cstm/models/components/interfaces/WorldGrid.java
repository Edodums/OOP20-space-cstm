package space.cstm.models.components.interfaces;

import space.cstm.utils.Pair;

import java.util.Map;
import java.util.Optional;

public interface WorldGrid {
    void initGrid();

    Map<Pair<Float, Float>, Optional<Entity>> getGrid();

    void updateGrid();

    void removeFromGrid(Entity entityToDelete);
}
