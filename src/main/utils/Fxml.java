package main.utils;

public enum Fxml {
    GAME("GAME"),
    MENU("MENU"),
    SETTINGS("SETTINGS");

    private final String filename;

    Fxml(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        // TODO: Refactor ( probably use StringBuilder ) or add constants for directories
        return  "../views/".concat(filename.toLowerCase())
                            .concat(".")
                            .concat(FileType.FXML.toString());
    }
}
