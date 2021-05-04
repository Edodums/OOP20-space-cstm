package space.cstm.models.components.interfaces;

import space.cstm.utils.Pair;

import java.util.Map;
import java.util.Optional;

public interface WorldGrid {
    /**
     *  creates instance of Entity and puts them into a grid
     */
    void initGrid();

    /**
     *
     * @return the grid with Entities instance / Optional.empty() if not present, and a pair representing a position
     */
    Map<Pair<Float, Float>, Optional<Entity>> getGrid();

    /**
     * Updates the position of every (Enemy) Entity instance
     */
    void updateGrid();

    /**
     *
     * @param entityToDelete
     */
    void removeFromGrid(Entity entityToDelete);
}
