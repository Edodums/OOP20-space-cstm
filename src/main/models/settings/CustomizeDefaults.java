package main.models.settings;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import main.models.settings.interfaces.CustomizableTypeImage;
import main.utils.enums.EntityType;
import main.utils.enums.Orientations;
import main.utils.enums.ResourcePath;
import main.utils.enums.WeaponType;

public class CustomizeDefaults {
  private static final Orientations orientation = Orientations.VERTICAL;
  private static final TypeImage weaponNpc = new TypeImage(ResourcePath.IMAGES_PATH + "enemy_laser.png", WeaponType.NPC, new Grid(3,5,1,1));
  private static final TypeImage weaponPlayer = new TypeImage(ResourcePath.IMAGES_PATH + "player_laser_default.png", WeaponType.PLAYER, new Grid(8,1,5,1));
  private static final TypeImage entityPlayer = new TypeImage(ResourcePath.IMAGES_PATH + "default_player_sprite.png", EntityType.PLAYER, new Grid());
  private static final TypeImage entityCommonShip = new TypeImage(ResourcePath.IMAGES_PATH + "space_invaders_sprite.png", EntityType.COMMONSHIP, new Grid(6,2,0,0,3));
  private static final TypeImage entityMotherShip = new TypeImage(ResourcePath.IMAGES_PATH + "mother_ship_sprites.png", EntityType.MOTHERSHIP, new Grid(6,2,3,0));

    /**
     * I take the fields of the class i am in through the use of interface
     */
  private CustomizeDefaults() {}

    /**
     * Check if at least one of the field in settings is null
     * @param settings
     * @return
     */
  public static boolean areDefaultsNeeded(final Settings settings) {
    Map<String, CustomizableTypeImage> typeImages = settings.getTypeImages();
    Orientations orientation = settings.getOrientation();
    
    return orientation == null || typeImages.entrySet().stream().anyMatch(entry -> entry.getValue() == null);
  }
    /**
     * In this method i make the comparison between the fields of this class and the fields of the settings class , by checking that they have the same names.
     * @param settings
     */
  public static void loadDefaults(final Settings settings)  {
    final Field[] fields = getFields();
    final Map<String, CustomizableTypeImage> typeImages = settings.getTypeImages();
    final Orientations orientation = settings.getOrientation();
    
    if (orientation == null) {
      settings.setOrientation(CustomizeDefaults.orientation);
    }
      /**
       * through the couple entrySet() and stream() i can make the filter for the objects that are not null
       */
    typeImages
          .entrySet()
          .stream()
          .filter(Objects::nonNull)
          .forEach(entry ->
            Arrays.stream(fields).forEach(field -> {  // check if the file is not static
              if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                return;
              }
  
              if (!field.getName().equals(entry.getKey())) {
                return;
              }
  
              try {
                TypeImage fieldTypeImage = (TypeImage) field.get(0); //keep the right field
                typeImages.putIfAbsent(entry.getKey(), fieldTypeImage); //key of the entry
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            })
          );
    
    settings.setTypeImages(typeImages); //takes the new type image and puts it into settings
  }
  
  private static Field[] getFields() {
    return CustomizeDefaults.class.getDeclaredFields();
  }
}
