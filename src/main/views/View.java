package main.views;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.beans.PropertyChangeListener;
import java.io.IOException;

public abstract class View implements PropertyChangeListener {
    /* Literally a number that I choose without any critical thinking */
    private static final double BOUND_FACTOR = 1.8;
    /* Primary Screen (useful if the user has multiple monitors bounds */
    private final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    private Scene scene;
    /* *
     * Pane is extended from all Parent object in fxml files that we have,
     * it makes sense to use it as a common field type
     * */
    private Pane parent;

    Scene getScene() {
        return this.scene;
    }

    Pane getParent() {
        return this.parent;
    }

    double getWidth() {
        return this.primaryScreenBounds.getWidth() / BOUND_FACTOR;
    }

    double getHeight() {
        return primaryScreenBounds.getHeight() / BOUND_FACTOR;
    }

    void setScene() {
        this.scene = new Scene(this.parent, getWidth(), getHeight());
    }

    final void setParent(String filename) {
        try {
            this.parent = FXMLLoader.load(getClass().getResource(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
