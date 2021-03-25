package main.models.components.interfaces;

import main.utils.Pair;
import main.utils.WeaponType;

public interface Weapon {
    void deploy(Pair<Integer, Integer> startingPoint, WeaponType weaponType);
}
