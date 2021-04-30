package main.models.settings.interfaces;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import main.models.settings.TypeImage;
import main.models.settings.deserializers.CustomizableTypeImageDeserializer;

@JsonDeserialize(as = TypeImage.class)
public interface CustomizableTypeImage {
    
    GridImage getGrid();
    
    String getName();
    
    Type getType();
    
    void setGrid(GridImage grid);
    
    void setType(Type type);
    
    void setName(String name);
    
}
