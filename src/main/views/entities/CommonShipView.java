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
  public Node create(Map<Pair<Double, Double>, Optional<Entity>> commonShips, double unit) {
    Group group = new Group();
    
    commonShips.forEach((key, value) -> {
      if (value.isEmpty()) {
        return;
      }
      
      ImageView commonShip = new ImageView(value.get().getFileName());
      commonShip.setTranslateX(key.getX() + unit);
      commonShip.setTranslateY(key.getY() + unit);
      
      group.getChildren().add(commonShip);
    });
    
    return group;
  }
}
