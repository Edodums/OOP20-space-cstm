package main.utils.enums;

public enum FileType {
    FXML("fxml");

    private final String type;

    FileType(String type) { this.type = type; }

    @Override
    public String toString() {
        return type;
    }
}
