package space.cstm.models.settings.interfaces;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import space.cstm.models.settings.TypeImage;
import space.cstm.models.settings.deserializers.CustomizableTypeImageDeserializer;

@JsonDeserialize(as = TypeImage.class)
public interface CustomizableTypeImage {
    
    GridImage getGrid();
    
    String getName();
    
    Type getType();
    
    void setGrid(GridImage grid);
    
    void setType(Type type);
    
    void setName(String name);
    
}
