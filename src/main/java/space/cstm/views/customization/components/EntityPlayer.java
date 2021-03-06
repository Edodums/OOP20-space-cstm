package space.cstm.views.customization.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import space.cstm.exceptions.DirectoryNotCreated;
import space.cstm.models.settings.interfaces.CustomizableTypeImage;
import space.cstm.models.settings.interfaces.GridImage;
import space.cstm.services.FileService;
import space.cstm.utils.enums.ResourcePath;
import space.cstm.views.customization.interfaces.CustomizableViewTypeImage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EntityPlayer implements CustomizableViewTypeImage, Initializable {
    private CustomizableTypeImage current;

    @FXML
    private TextField entityPlayerFilename;
    @FXML
    private Group entityPlayer;
    @FXML
    private Label entityPlayerLabel;
    @FXML
    private CheckBox entityPlayerIsDivided;
    @FXML
    private ImageView entityPlayerImage;
    @FXML
    private TextField entityPlayerSelectedColumn;
    @FXML
    private TextField entityPlayerSelectedRow;
    @FXML
    private TextField entityPlayerColumns;
    @FXML
    private TextField entityPlayerRows;
    @FXML
    private TextField entityPlayerInternalColumns;

    public EntityPlayer() {}

    @FXML
    public void entityPlayerChooseImage() {
        File selectedFile = getFileChooser().showOpenDialog(this.entityPlayer.getScene().getWindow());

        if (selectedFile != null) {
            try {
                String filename = FileService.saveFile(selectedFile, ResourcePath.EXTERNAL_IMAGES_PATH.toString(),true);
                setFilename(filename);
            } catch (IllegalAccessException | DirectoryNotCreated | IOException e) {
                e.printStackTrace();
            }

            final Image image = new Image(selectedFile.toURI().toString());
            setImage(image);
        }
    }

    @Override
    public String getLabel() {
        return this.entityPlayerLabel.getText();
    }

    @Override
    public String getGroupId() {
        return this.entityPlayer.getId();
    }

    @Override
    public void toggleGridTextFields(boolean value) {
        this.entityPlayerRows.setDisable(!value);
        this.entityPlayerColumns.setDisable(!value);
        this.entityPlayerSelectedRow.setDisable(!value);
        this.entityPlayerSelectedColumn.setDisable(!value);
        this.entityPlayerInternalColumns.setDisable(!value);

        final float opacityValue = value ? 1.0f : 0.0f;

        this.entityPlayerRows.setOpacity(opacityValue);
        this.entityPlayerColumns.setOpacity(opacityValue);
        this.entityPlayerSelectedRow.setOpacity(opacityValue);
        this.entityPlayerSelectedColumn.setOpacity(opacityValue);
        this.entityPlayerInternalColumns.setOpacity(opacityValue);
    }

    @Override
    public void handleRows() {
        this.entityPlayerRows.textProperty().addListener((observable, oldValue, newValue) -> setRows(getFormattedNumericValue(newValue)));
    }

    @Override
    public void handleColumns() {
        this.entityPlayerColumns.textProperty().addListener((observable, oldValue, newValue) -> setColumns(getFormattedNumericValue(newValue)));
    }

    @Override
    public void handleSelectedRow() {
        this.entityPlayerSelectedRow.textProperty().addListener((observable, oldValue, newValue) -> setSelectedRow(getFormattedNumericValue(newValue)));
    }

    @Override
    public void handleSelectedColumn() {
        this.entityPlayerSelectedColumn.textProperty().addListener((observable, oldValue, newValue) -> setSelectedColumn(getFormattedNumericValue(newValue)));
    }

    @Override
    public void handleInternalColumns() {
        this.entityPlayerInternalColumns.textProperty().addListener((observable, oldValue, newValue) -> setInternalColumns(getFormattedNumericValue(newValue)));
    }

    @Override
    public void setRows(String value) {
        this.entityPlayerRows.setText(value);
    }

    @Override
    public void setColumns(String value) {
        this.entityPlayerColumns.setText(value);
    }

    @Override
    public void setSelectedRow(String value) {
        this.entityPlayerSelectedRow.setText(value);
    }

    @Override
    public void setSelectedColumn(String value) {
        this.entityPlayerSelectedColumn.setText(value);
    }

    @Override
    public void setInternalColumns(String value) {
        this.entityPlayerInternalColumns.setText(value);
    }

    @Override
    public Integer getRows() {
        return getNumericValue(this.entityPlayerRows.getText());
    }

    @Override
    public Integer getColumns() {
        return getNumericValue(this.entityPlayerColumns.getText());
    }

    @Override
    public Integer getSelectedRow() {
        return getNumericValue(this.entityPlayerSelectedRow.getText());
    }

    @Override
    public Integer getSelectedColumn() {
        return getNumericValue(this.entityPlayerSelectedColumn.getText());
    }

    @Override
    public Integer getInternalColumns() {
        return getNumericValue(this.entityPlayerInternalColumns.getText());
    }

    @Override
    public void setTypeImage(CustomizableTypeImage typeImage) {
        this.current = typeImage;
    }

    @Override
    public CustomizableTypeImage getTypeImages() {
        return this.current;
    }

    @Override
    public void setImage(Image image) {
        this.entityPlayerImage.setImage(image);
    }

    @Override
    public void setFilename(String filename) {
        this.entityPlayerFilename.setText(filename);
    }

    @Override
    public String getFilename() {
        return this.entityPlayerFilename.getText();
    }

    @Override
    public void setDefaults() {
        final GridImage grid = getTypeImages().getGrid();

        this.entityPlayerFilename.setText(getTypeImages().getName());
        this.entityPlayerImage.setImage(new Image(getTypeImages().getName()));

        this.entityPlayerRows.setText(String.valueOf(grid.getRows()));
        this.entityPlayerColumns.setText(String.valueOf(grid.getColumns()));
        this.entityPlayerSelectedRow.setText(String.valueOf(grid.getSelectedRow()));
        this.entityPlayerSelectedColumn.setText(String.valueOf(grid.getSelectedColumn()));
        this.entityPlayerInternalColumns.setText(String.valueOf(grid.getInternalColumns()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityPlayerIsDivided.selectedProperty().addListener((observable, oldValue, newValue) -> toggleGridTextFields(observable.getValue()));

        handleRows();
        handleColumns();
        handleSelectedRow();
        handleSelectedColumn();
        handleInternalColumns();
    }
}

