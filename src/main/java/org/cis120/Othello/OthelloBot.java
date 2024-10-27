package org.cis120.Othello;

import java.util.Map;
import java.util.TreeMap;

public class OthelloBot {
    private int depth;

    public OthelloBot() {
        depth = 4;
    }

    public OthelloBot(int d) {
        depth = d;
        if (depth < 1) {
            depth = 1;
        }
    }

    public Position findMove(OthelloBoard board, boolean isBlack) {
        TreeMap<Position, OthelloBoard> moves = board.generateLegalMoves(isBlack);
        int bestVal = -65;
        Position bestPos = null;
        for (Map.Entry<Position, OthelloBoard> entry : moves.entrySet()) {
            int value = miniMaxVal(entry.getValue(), -65, 65, false, isBlack, depth - 1);
            if (value > bestVal) {
                bestPos = entry.getKey();
                bestVal = value;
            }
        }
        //System.out.println("Best Move: (" + bestPos.getRow() + ", " + bestPos.getCol() + ")");
        return bestPos;
    }

    public int miniMaxVal(OthelloBoard board, int alpha, int beta, boolean maxPlayer,
                          boolean maxPlayerBlack, int d) {
        TreeMap<Position, OthelloBoard> moves =
                board.generateLegalMoves(!(maxPlayer ^ maxPlayerBlack));
        if (moves.isEmpty() || d == 0) {
            return board.evaluateBoard(maxPlayerBlack);
        }

        if (maxPlayer) {
            int maxVal = -65;
            for (OthelloBoard b : moves.values()) {
                int val = miniMaxVal(b, alpha, beta, false, maxPlayerBlack, d - 1);
                maxVal = Math.max(maxVal, val);
                alpha = Math.max(alpha, val);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxVal;
        } else {
            int minVal = 65;
            for (OthelloBoard b : moves.values()) {
                int val = miniMaxVal(b, alpha, beta, true, maxPlayerBlack, d - 1);
                minVal = Math.min(minVal, val);
                beta = Math.min(beta, val);
                if (beta <= alpha) {
                    break;
                }
            }
            return minVal;
        }
    }
}
