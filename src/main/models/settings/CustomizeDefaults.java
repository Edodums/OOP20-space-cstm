package main.models.settings;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import main.utils.enums.EntityType;
import main.utils.enums.Orientations;
import main.utils.enums.ResourcePath;
import main.utils.enums.WeaponType;

public class CustomizeDefaults {
  private static final Orientations orientation = Orientations.VERTICAL;
  private static final TypeImage weaponNpc = new TypeImage(ResourcePath.IMAGES_PATH + "enemy_laser.png", WeaponType.NPC, new Grid(3,5,1,1));
  private static final TypeImage weaponPlayer = new TypeImage(ResourcePath.IMAGES_PATH + "player_laser.png", WeaponType.PLAYER, new Grid(1,8,1,1));
  private static final TypeImage entityPlayer = new TypeImage(ResourcePath.IMAGES_PATH + "default_player_sprite.png", EntityType.PLAYER, new Grid());
  private static final TypeImage entityCommonShip = new TypeImage(ResourcePath.IMAGES_PATH + "space_invaders_sprite.png", EntityType.COMMONSHIP, new Grid(6,2,0,0,3));
  private static final TypeImage entityMotherShip = new TypeImage(ResourcePath.IMAGES_PATH + "mother_ship_sprites.png", EntityType.MOTHERSHIP, new Grid(6,2,3,0));

  private CustomizeDefaults() {}
  
  public static boolean areDefaultsNeeded(final Settings settings) {
    Map<String, TypeImage> typeImages = settings.getTypeImages();
    Orientations orientation = settings.getOrientation();
    
    return orientation == null || typeImages.entrySet().stream().anyMatch(entry -> entry.getValue() == null);
  }
  
  public static void loadDefaults(final Settings settings)  {
    final Field[] fields = getFields();
    final Map<String, TypeImage> typeImages = settings.getTypeImages();
    final Orientations orientation = settings.getOrientation();
    
    if (orientation == null) {
      settings.setOrientation(CustomizeDefaults.orientation);
    }
    
    typeImages
          .entrySet()
          .stream()
          .filter(Objects::nonNull)
          .forEach(entry ->
            Arrays.stream(fields).forEach(field -> {
              if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                return;
              }
  
              if (!field.getName().equals(entry.getKey())) {
                return;
              }
  
              try {
                TypeImage fieldTypeImage = (TypeImage) field.get(0);
                typeImages.putIfAbsent(entry.getKey(), fieldTypeImage);
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            })
          );
    
    settings.setTypeImages(typeImages);
  }
  
  private static Field[] getFields() {
    return CustomizeDefaults.class.getDeclaredFields();
  }
}
