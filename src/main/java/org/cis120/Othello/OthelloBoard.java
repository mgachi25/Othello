package org.cis120.Othello;
import java.util.TreeMap;

public class OthelloBoard {
    private int boardSize = 10;
    private int blackScore;
    private int whiteScore;
    private int[][] board;

    public OthelloBoard() {
        board = new int[boardSize][boardSize];
        board[(boardSize / 2) - 1][(boardSize / 2) - 1] = 1;
        board[(boardSize / 2) - 1][(boardSize / 2)] = -1;
        board[boardSize / 2][(boardSize / 2) - 1] = -1;
        board[boardSize / 2][boardSize / 2] = 1;
        blackScore = 2;
        whiteScore = -2;
    }

    public OthelloBoard(int[][] b, int bS, int wS) {
        board = b;
        blackScore = bS;
        whiteScore = wS;
    }

    public void updateScores(int flipped, boolean blackPlaying) {
        if (blackPlaying) {
            blackScore += (1 + flipped);
            whiteScore += flipped;
        } else {
            whiteScore -= (1 + flipped);
            blackScore -= flipped;
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public TreeMap<Position, OthelloBoard> generateLegalMoves(boolean blackPlaying) {
        TreeMap<Position, OthelloBoard> moves = new TreeMap<Position, OthelloBoard>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Position pos = new Position(i, j);
                if (isLegalMove(pos, blackPlaying)) {
                    OthelloBoard newBoard = playMove(pos, blackPlaying);
                    moves.put(pos, newBoard);
                }
            }
        }
        return moves;
    }

    public OthelloBoard playMove(Position pos, boolean blackPlaying) { //assumes move is legal
        int row = pos.getRow();
        int col = pos.getCol();
        int myPiece = 1;
        if (!blackPlaying) {
            myPiece = -1;
        }
        int[][] newBoard = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        newBoard[row][col] = myPiece;
        int totalFlipped = 0;

        OthelloBoard newB = new OthelloBoard(newBoard, blackScore, whiteScore);

        if (checkDirection(new Position(row - 1, col - 1), -1, -1, blackPlaying)) {
            totalFlipped += newB.playDirection(new Position(row - 1, col - 1),
                    -1, -1, blackPlaying);
        }
        if (checkDirection(new Position(row - 1, col), -1, 0, blackPlaying)) {
            totalFlipped += newB.playDirection(new Position(row - 1, col), -1, 0, blackPlaying);
        }
        if (checkDirection(new Position(row - 1, col + 1), -1, 1, blackPlaying)) {
            totalFlipped += newB.playDirection(new Position(row - 1, col + 1), -1, 1, blackPlaying);
        }
        if (checkDirection(new Position(row, col - 1), 0, -1, blackPlaying)) {
            totalFlipped += newB.playDirection(new Position(row, col - 1), 0, -1, blackPlaying);
        }
        if (checkDirection(new Position(row, col + 1), 0, 1, blackPlaying)) {
            totalFlipped += newB.playDirection(new Position(row, col + 1), 0, 1, blackPlaying);
        }
        if (checkDirection(new Position(row + 1, col - 1), 1, -1, blackPlaying)) {
            totalFlipped += newB.playDirection(new Position(row + 1, col - 1), 1, -1, blackPlaying);
        }
        if (checkDirection(new Position(row + 1, col), 1, 0, blackPlaying)) {
            totalFlipped += newB.playDirection(new Position(row + 1, col), +1, 0, blackPlaying);
        }
        if (checkDirection(new Position(row + 1, col + 1), 1, 1, blackPlaying)) {
            totalFlipped += newB.playDirection(new Position(row + 1, col + 1), 1, 1, blackPlaying);
        }

        newB.updateScores(totalFlipped, blackPlaying);

        return newB;
    }


    public boolean isLegalMove(Position pos, boolean blackPlaying) {
        int row = pos.getRow();
        int col = pos.getCol();
        int myPiece = 1;
        if (!blackPlaying) {
            myPiece = -1;
        }


        if (row < 0 || col < 0 || row > boardSize || col > boardSize) {
            return false;
        }
        if (board[row][col] != 0) {
            return false;
        }
        if (checkDirection(new Position(row - 1, col - 1), -1, -1, blackPlaying)) {
            return true;
        }
        if (checkDirection(new Position(row - 1, col), -1, 0, blackPlaying)) {
            return true;
        }
        if (checkDirection(new Position(row - 1, col + 1), -1, 1, blackPlaying)) {
            return true;
        }
        if (checkDirection(new Position(row, col - 1), 0, -1, blackPlaying)) {
            return true;
        }
        if (checkDirection(new Position(row, col + 1), 0, 1, blackPlaying)) {
            return true;
        }
        if (checkDirection(new Position(row + 1, col - 1), 1, -1, blackPlaying)) {
            return true;
        }
        if (checkDirection(new Position(row + 1, col), 1, 0, blackPlaying)) {
            return true;
        }
        if (checkDirection(new Position(row + 1, col + 1), 1, 1, blackPlaying)) {
            return true;
        }

        return false;
    }

    public boolean checkDirection(Position pos, int dR, int dC, boolean blackPlaying) {
        int row = pos.getRow();
        int col = pos.getCol();
        int oppPiece = -1;
        if (!blackPlaying) {
            oppPiece = 1;
        }
        if ((row >= 0 && col >= 0 && row < boardSize && col < boardSize)
                && board[row][col] == oppPiece) {
            while (row >= 0 && col >= 0 && row < boardSize && col < boardSize) {
                row += dR;
                col += dC;
                if ((row >= 0 && col >= 0 && row < boardSize && col < boardSize)) {
                    if (board[row][col] == 0) {
                        return false;
                    }
                    if (board[row][col] == -1 * oppPiece) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public int playDirection(Position pos, int dR, int dC, boolean blackPlaying) {
        int row = pos.getRow();
        int col = pos.getCol();
        int oppPiece = -1;
        if (!blackPlaying) {
            oppPiece = 1;
        }
        int numFlipped = 0;
        while (board[row][col] == oppPiece) {
            board[row][col] = -1 * oppPiece;
            numFlipped++;
            row += dR;
            col += dC;
        }

        return numFlipped;
    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            String s = "";
            for (int j = 0; j < boardSize; j++) {
                s = s + board[i][j] + "  ";
            }
        }
    }

    public int getBlackScore() {
        return blackScore;
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public int getCell(Position pos) {
        return board[pos.getRow()][pos.getCol()];
    }

    public int evaluateBoard(boolean isBlack) {
        if (isBlack) {
            return blackScore + whiteScore;
        } else {
            return -1 * (blackScore + whiteScore);
        }
    }

}
