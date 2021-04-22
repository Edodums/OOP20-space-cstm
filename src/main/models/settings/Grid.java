package main.models.settings;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Grid {
    @JsonSerialize
    private final int rows;
    @JsonSerialize
    private final int columns;
    @JsonSerialize
    private final int selectedRow;
    @JsonSerialize
    private final int selectedColumn;

    public Grid(final Integer rows, final Integer columns, final Integer selectedRow, final Integer selectedColumn) {
        this.rows = rows;
        this.columns = columns;
        this.selectedRow = selectedRow;
        this.selectedColumn = selectedColumn;
    }

    public Grid() {
        this.rows = 1;
        this.columns = 1;
        this.selectedRow = 1;
        this.selectedColumn = 1;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }

    public int getSelectedRow() {
        return selectedRow;
    }
}
