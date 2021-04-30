package main.models.settings.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import main.models.settings.Grid;

@JsonDeserialize(as = Grid.class)
public interface GridImage {
    
    int getRows();
    
    int getColumns();
    
    int getSelectedColumn();
    
    int getSelectedRow();
    
    int getInternalColumns();
}
