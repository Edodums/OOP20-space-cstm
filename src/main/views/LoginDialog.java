package main.views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.controllers.GameController;

public class LoginDialog extends Dialog<String>  {
    private static final String SAVE = "Save";
    private static final String PLAYER_LABEL = "Player Name";
    private static final String PLAYER_PROMPT_TEXT = "Player Name here";
    private static final String TITLE_TEXT = "Set the Player name";
    private static final String HEADER_TEXT = "If you don't put one, how we'll know who's the best?";

    private static final Integer MIN_LENGTH_PLAYER_NAME = 3;

    private final Label userLabel = new Label(PLAYER_LABEL);
    private final TextField playerName = new TextField();
    

    LoginDialog(GameController controller) {
        setDialogTexts();
    
        final ButtonType saveButtonType = new ButtonType(SAVE, ButtonBar.ButtonData.OK_DONE);
        
        getDialogPane().getButtonTypes().addAll( saveButtonType, ButtonType.CANCEL );
        getDialogPane().setContent( createVBox() );
        
        final Button saveButton = (Button) this.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        playerName.textProperty().addListener((observable, oldValue, newValue ) ->
                saveButton.setDisable(newValue.trim().isEmpty() && newValue.length() > MIN_LENGTH_PLAYER_NAME)
        );
    
        

        Platform.runLater(playerName::requestFocus);

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return playerName.getText();
            }

            return null;
        });

        showAndWait().ifPresent(controller::setPlayerName);
    }

    private void setDialogTexts()  {
        setTitle(TITLE_TEXT);
        setHeaderText(HEADER_TEXT);

        playerName.setPromptText(PLAYER_PROMPT_TEXT);
    }

    private VBox createVBox() {
        VBox vbox = new VBox(userLabel, playerName);
        vbox.setSpacing( 10.0d );
        vbox.setPadding( new Insets(40.0d) );

        return vbox;
    }
}
