package org.cis120.Othello;

public class Position implements Comparable<Position> {
    private int row;
    private int col;

    public Position() {
        row = 0;
        col = 0;
    }

    public Position(int r, int c) {
        row = r;
        col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int r) {
        row = r;
    }

    public void setCol(int c) {
        col = c;
    }

    @Override
    public int compareTo(Position p) {
        if (row == p.getRow() && col == p.getCol()) {
            return 0;
        } else if (row < p.getRow()) {
            return -1;
        } else {
            return 1;
        }
    }
}
