package main.models.components.weapons;

import main.exceptions.WeaponNotFoundException;
import main.models.components.interfaces.Weapon;
import main.utils.enums.WeaponType;

import java.util.HashMap;
import java.util.Map;

public class WeaponFactory {
    private static final Map<WeaponType, Weapon> weaponTypes = new HashMap<>();

    public Weapon getWeapon(WeaponType weaponType) {
        return weaponTypes.computeIfAbsent(weaponType, WeaponFactory::getWeaponClass);
    }

    private static Weapon getWeaponClass(WeaponType weaponType) {
        return switch (weaponType) {
            case PLAYER -> new PlayerBeam();
            default -> throw new WeaponNotFoundException(); // TODO: delete this after you have made the NPC class
        };
    }
}
