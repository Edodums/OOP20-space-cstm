package main.utils.enums;

import java.io.File;

public enum ResourcePath {
    VIEWS_PATH("../../resources/layout/"),
    IMAGES_PATH("../../resources/assets/images/");

    private final String path;

    ResourcePath(String path) { this.path = path; }

    @Override
    public String toString() {
        return path.replaceAll("\\\\/", File.separator);
    }
}
