package main.models.settings;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import main.models.settings.interfaces.GridImage;

public class Grid implements GridImage {
    @JsonSerialize
    private final int rows;
    @JsonSerialize
    private final int columns;
    @JsonSerialize
    private final int selectedRow;
    @JsonSerialize
    private final int selectedColumn;
    @JsonSerialize
    private final int internalColumns;
    
    public Grid(final int rows,  final int columns,final int selectedRow, final int selectedColumn, final int internalColumns) {
        this.rows = rows;
        this.columns = columns;
        this.selectedRow = selectedRow;
        this.selectedColumn = selectedColumn;
        this.internalColumns = internalColumns;
    }
    
    public Grid(final Integer rows, final Integer columns, final Integer selectedRow, final Integer selectedColumn) {
        this.rows = rows;
        this.columns = columns;
        this.selectedRow = selectedRow;
        this.selectedColumn = selectedColumn;
        this.internalColumns = 0;
    }

    public Grid() {
        this.rows = 0;
        this.columns = 0;
        this.selectedRow = 0;
        this.selectedColumn = 0;
        this.internalColumns = 0;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int getSelectedColumn() {
        return selectedColumn;
    }

    @Override
    public int getSelectedRow() {
        return selectedRow;
    }
    
    @Override
    public int getInternalColumns() {
        return internalColumns;
    }
}
