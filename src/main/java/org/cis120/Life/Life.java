package org.cis120.Life;

public class Life {
    private static final int SIZE = 100;
    private int[][] LifeBoard;
    private boolean hasStarted;

    public Life() {
        LifeBoard = new int[SIZE][SIZE];
        hasStarted = false;
    }

    public void reset() {
        LifeBoard = new int[SIZE][SIZE];
        hasStarted = false;
    }

    public void startRun() {
        hasStarted = true;
    }

    public int getCell(int r, int c) {
        return LifeBoard[r][c];
    }

    public boolean isRunning() {
        return hasStarted;
    }

    public void addCell(int r, int c) {
        LifeBoard[r][c] = 1;
    }

    public void updateBoard() {
        int[][] newB = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                updateCell(i,j,newB);
            }
        }
        LifeBoard = newB;
    }

    public int boardSize() {
        return SIZE;
    }

    public void updateCell(int r, int c, int[][] newB) {
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int x = r + i;
                int y = c + j;
                if (x < 0) x = SIZE + x;
                if (y < 0) y = SIZE + y;
                count += LifeBoard[(x) % SIZE][(y) % SIZE];
            }
        }
        if (count == 3) {
            newB[r][c] = 1;
        } else if (LifeBoard[r][c] == 1 && count == 4){
            newB[r][c] = 1;
        } else newB[r][c] = 0;
    }
}
