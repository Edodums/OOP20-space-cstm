package main.views.entities;

import java.util.Map;
import java.util.Optional;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.models.components.interfaces.Entity;
import main.utils.Pair;
import main.views.entities.interfaces.EntitySprite;

public class CommonShipView implements EntitySprite {
  
  @Override
  public Node create(Map<Pair<Integer, Integer>, Optional<Entity>> commonShips) {
    Group group = new Group();
    
    commonShips.forEach((key, value) -> {
      if (value.isEmpty()) {
        return;
      }
      
      ImageView commonShip = new ImageView(value.get().getFileName());
      // TODO: add unit ( 1 square/rectangle )
      commonShip.setTranslateX(key.getX());
      commonShip.setTranslateY(key.getY());
      
      group.getChildren().add(commonShip);
    });
    
    return group;
  }
}
