package space.cstm.utils.enums;

import space.cstm.services.JarService;

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
        String viewPath = ResourcePath.VIEWS_PATH.toString();

        if (JarService.runningFromJar()) {
            viewPath = ResourcePath.JAR_VIEWS_PATH.toString();
        }

        return viewPath
                .concat(filename)
                .concat(".")
                .concat(FileType.FXML.toString());
    }
}
