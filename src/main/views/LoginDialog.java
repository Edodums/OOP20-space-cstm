package main.views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.models.Game;

public class LoginDialog extends Dialog<String> {
    private static final String SAVE = "Save";
    private static final String PLAYER_LABEL = "Player Name";
    private static final String PLAYER_PROMPT_TEXT = "Player Name here";
    private static final String TITLE_TEXT = "Set the Player name";
    private static final String HEADER_TEXT = "If you don't put one, how we'll know who's the best?";

    private static final Integer MIN_LENGHT_PLAYER_NAME = 3;

    private final Label userLabel = new Label(PLAYER_LABEL);
    private final ButtonType saveButtonType = new ButtonType(SAVE, ButtonBar.ButtonData.OK_DONE);
    private final TextField playerName = new TextField();
    private final Button saveButton = (Button) this.getDialogPane().lookupButton(saveButtonType);

    LoginDialog(Game game) {
        setDialogTexts();

        saveButton.setDisable(true);

        playerName.textProperty().addListener((observable, oldValue, newValue ) ->
                saveButton.setDisable(newValue.trim().isEmpty() && newValue.length() > MIN_LENGHT_PLAYER_NAME)
        );

        Platform.runLater(playerName::requestFocus);

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return playerName.getText();
            }

            return null;
        });

        showAndWait().ifPresent(game::setPlayerName);

        DialogPane dialogPane = getDialogPane();
        dialogPane.getButtonTypes().addAll( saveButtonType, ButtonType.CANCEL );
        dialogPane.setContent( createVBox() );
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
