package main.utils.enums;

import java.io.File;
import java.util.regex.Pattern;


public enum ResourcePath {
    HOME_PATH(System.getProperty("user.home")),
    VIEWS_PATH("../../resources/layout/"),
    SETTINGS_COMPONENTS("./src/resources/layout/setting"),
    EXTERNAL_DATA_PATH(ResourcePath.HOME_PATH + "/space-cstm/space-cstm-data"),
    DATA_PATH("./src/resources/data/"),
    EXTERNAL_IMAGES_PATH(ResourcePath.HOME_PATH + "/space-cstm/space-cstm-images"),
    IMAGES_PATH("resources/assets/images/");

    private final String path;

    ResourcePath(String path) { this.path = path; }

    @Override
    public String toString() {
        return Pattern.compile("\\\\/")
                     .matcher(path)
                     .replaceAll(fileSeparator -> File.separator);
    }
}
