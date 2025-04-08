package com.TrueWordHunt.Util;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true; // if point to same, the same
        if (!(that instanceof Position)) return false; // if that isn't a Position, can't be the same
        Position thatPrime = (Position) that; //
        return this.row == thatPrime.row && this.col == thatPrime.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    public boolean nextTo(Position that) {
        if (that == null) return false;
        if (this.equals(that)) return false;

        int rowDist = Math.abs(this.row - that.row);
        int colDist = Math.abs(this.col - that.col);

        // Exactly one coordinate differs by 1 and the other by 0
        return rowDist <= 1 && colDist <= 1;
    }
}
