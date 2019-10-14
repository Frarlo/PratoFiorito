package pratofiorito.utils;

public class Pos implements Cloneable {
    private int row;
    private int col;
    
    public Pos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Pos addRow(int toAdd) {
        this.row += toAdd;
        return this;
    }

    public Pos addCol(int toAdd) {
        this.col += toAdd;
        return this;
    }
    
    @Override
    public Pos clone() {
        try {
            return (Pos) super.clone();
        } catch (CloneNotSupportedException unreachable) {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return "Pos{" + "row=" + row + ", col=" + col + '}';
    }
}
