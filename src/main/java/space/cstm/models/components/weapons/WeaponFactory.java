package space.cstm.models.components.weapons;

import java.util.EnumMap;
import java.util.Map;
import space.cstm.exceptions.WeaponNotFoundException;
import space.cstm.models.components.interfaces.Weapon;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.utils.enums.WeaponType;

public class WeaponFactory {
    private static final Map<WeaponType, Weapon> weaponTypes = new EnumMap<>(WeaponType.class);

    public Weapon getWeapon(CustomizableTypeImage typeImage) {
        final WeaponType weaponType = (WeaponType) typeImage.getType();
        
        if (weaponTypes.containsKey(weaponType)) {
            return weaponTypes.get(weaponType);
        }
        
        final Weapon weapon = getWeaponClass(typeImage);
        
        weaponTypes.put(weaponType, weapon);
        
        return weapon;
    }

    private Weapon getWeaponClass(CustomizableTypeImage typeImage) {
        final WeaponType weaponType = (WeaponType) typeImage.getType();
        final Weapon weapon;
        
        switch (weaponType) {
            case PLAYER :
                weapon = new PlayerBeam(typeImage);
                break;
            
            case NPC :
                weapon = new EnemyBeam(typeImage);
                break;
            default : throw new WeaponNotFoundException();
        }
        
        return weapon;
    }
}
