package main.utils;

public enum FileType {
    FXML("FXML");

    private final String type;

    FileType(String type) { this.type = type; }

    @Override
    public String toString() {
        return type.toLowerCase();
    }
}
