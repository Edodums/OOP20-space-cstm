package main.models;

public class Grid {
    private final int rows;
    private final int columns;
    private final int rowSelected;
    private final int columnSelected;

    public Grid(Integer rows, Integer columns, Integer rowSelected, Integer columnSelected) {

        this.rows = rows;
        this.columns = columns;
        this.rowSelected = rowSelected;
        this.columnSelected = columnSelected;
    }

    public Grid() {
        this.rows = 1;
        this.columns = 1;
        this.rowSelected = 1;
        this.columnSelected = 1;

    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getColumnSelected() {
        return columnSelected;
    }

    public int getRowSelected() {
        return rowSelected;
    }
}
