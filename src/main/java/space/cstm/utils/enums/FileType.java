package space.cstm.utils.enums;

public enum FileType {
    FXML("fxml"),
    YAML("yaml");

    private final String type;

    FileType(String type) { this.type = type; }

    @Override
    public String toString() {
        return type;
    }
}
