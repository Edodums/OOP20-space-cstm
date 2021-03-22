package main.models.components;

import main.models.components.interfaces.Entity;
import main.utils.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class CommonShip implements Entity {
    private static final Integer COLUMNS = 8;
    private static final Integer ROWS = 3;
    private static final Integer NEXT_ROWS = 6;
    private static final Pair<Integer,Integer> STARTING_POINT = new Pair<>(3, 1);

    public CommonShip() {
        // empty
    }

    @Override
    public void move(double velocity) {
        // empty
    }

    @Override
    public void shoot() {
        // empty
    }

    @Override
    public void die() {
        System.out.println("Commoship is dead!");
    }

    @Override
    public Integer getSpawnNumber() {
        return getCommonEnemiesColumns() * getCommonEnemiesRows();
    }

    @Override
    public Map<Pair<Integer, Integer>, Optional<Entity>> create() {
        Map<Pair<Integer, Integer>, Optional<Entity>> army = new HashMap<>();

        IntStream.range(0, getCommonEnemiesColumns())
                .boxed()
                .flatMap(x -> IntStream.range(0, getCommonEnemiesRows())
                                       .mapToObj(y -> {
                                           int positionX = x + getStartingPoint().getX();
                                           int positionY = y + getStartingPoint().getY();

                                           return new Pair<>(positionX, positionY);
                                       }))
                .forEach(pair -> army.put(pair, Optional.of(this)));

        return army;
    }

    public Map<Pair<Integer, Integer>, CommonShip> updateArmyPosition(Map<Pair<Integer, Integer>, CommonShip> currentArmy) {
        Map<Pair<Integer, Integer>, CommonShip> updatedArmy = new HashMap<>();
        // TODO: check this algorithm, don't OVERTHINK IT, refactor if needed
        // TODO: speed factor
        currentArmy.forEach((pair, entity) -> {
            Pair<Integer, Integer> newPair = null;

            /* 1. if you've reach the end you set take away 1 life from player counter. */
            if (pair.getX().equals(getCommonEnemiesColumns()) &&
                pair.getY().equals(getCommonEnemiesRows() + getCommonEnemiesNextRows())) {
                //TODO: probably I'll handle it with an Event ( if so, I have to create an Event Manager )
                System.out.println("REACHING END");
                return;
            }

            /* 2. if you've to go right then check if the current number (x) is lower than the next one */
            if (!isComingRight(pair.getY())) {
                newPair = new Pair<>(pair.getX() + 1, pair.getY());
            }

            /* 3. if you've to go left then check if the current number (x) is greater than the next one */
            if (isComingRight(pair.getY())) {
                newPair = new Pair<>(pair.getX() - 1, pair.getY());
            }

            /* 4. if you reach the last column then go down and back (x - 1) && check if coming from the left */
            if (pair.getX().equals(getCommonEnemiesColumns()) && !isComingRight(pair.getY())) {
                newPair = new Pair<>(pair.getX() - 1, pair.getY() + 1);
            }

            /* 5. if you reach the first column then go down and forth ( x + 1 ) && check if coming from the right */
            if (pair.getX().equals(getStartingPoint().getX()) && isComingRight(pair.getY())) {
                newPair = new Pair<>(pair.getX() + 1, pair.getY() + 1);
            }

            if (newPair == null) {
                throw new NullPointerException("Not really an expected scenario");
            }

            updatedArmy.put(new Pair<>(newPair.getX(), newPair.getY()), entity);
        });

        return updatedArmy;
    }


    private boolean isComingRight(Integer currentY) {
        return currentY % 2 == 0;
    }

    private static Integer getCommonEnemiesColumns() {
        return COLUMNS;
    }

    private static Integer getCommonEnemiesRows() {
        return ROWS;
    }

    public static Integer getCommonEnemiesNextRows() {
        return NEXT_ROWS;
    }

    private static Pair<Integer, Integer> getStartingPoint() { return STARTING_POINT; }
}
