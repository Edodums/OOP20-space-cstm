package main.components.interfaces;

import javafx.scene.Node;

public interface Entity {
    void move(Node node, double velocity);
    void shoot();
    void die();
    void looseLife();
}
