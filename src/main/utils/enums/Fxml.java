package main.utils.enums;

public enum Fxml {
    GAME("game"),
    MENU("menu"),
    SETTINGS("settings"),
    RANKING("ranking");

    private final String filename;

    Fxml(String filename){
        this.filename = filename;
    }

    public String getFilePath(){
        return ResourcePath.VIEWS_PATH.toString()
                .concat(filename)
                .concat(".")
                .concat(FileType.FXML.toString());
    }
}
