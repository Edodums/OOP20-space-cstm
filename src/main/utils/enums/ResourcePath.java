package main.utils.enums;

public enum ResourcePath {
    VIEWS_PATH("../../resources/layout/"),
    IMAGES_PATH("../../resources/assets/images/");

    private final String path;

    ResourcePath(String path) { this.path = path; }

    @Override
    public String toString() {
        return path;
    }
}
