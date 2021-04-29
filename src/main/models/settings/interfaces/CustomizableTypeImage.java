package main.models.settings.interfaces;


public interface CustomizableTypeImage {
    GridImage getGrid();
    String getName();
    Type getType();

    void setGrid(GridImage grid);
    void setType(Type type);
    void setName(String name);
}
