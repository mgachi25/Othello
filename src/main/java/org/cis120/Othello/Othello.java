package org.cis120.Othello;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;


/**
 * This class is a model for Othello
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 */
public class Othello {

    private OthelloBoard board;
    private boolean blackPlaying;
    private boolean gameOver;
    private boolean whiteIsBot;
    private boolean blackIsBot;
    private OthelloBot bot;

    /**
     * Constructor sets up game state.
     */
    public Othello() {
        reset();
    }

    public TreeMap<Position, OthelloBoard> getMoves() {
        return board.generateLegalMoves(blackPlaying);
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     */
    public boolean playerTurn(Position pos) {
        if (gameOver) {
            return false;
        }
        if (board.isLegalMove(pos, blackPlaying)) {
            board = board.playMove(pos, blackPlaying);
            if (checkWinner() == 0) {
                switchPlayer();
            }
            return true;
        }
        return false;
    }

    public boolean botTurn() {
        if (gameOver) {
            return false;
        }
        Position pos = bot.findMove(board, blackPlaying);
        board = board.playMove(pos, blackPlaying);
        if (checkWinner() == 0) {
            switchPlayer();
        }
        return true;
    }

    public void switchPlayer() {
        blackPlaying = !blackPlaying;
    }

    public boolean isBotPlaying() {
        return ((whiteIsBot && !blackPlaying) || (blackIsBot && blackPlaying));
    }

    public void toggleBlackAI() {
        blackIsBot = !blackIsBot;
    }

    public void toggleWhiteAI() {
        whiteIsBot = !whiteIsBot;
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     * has won, 3 if the game hits stalemate
     */
    public int checkWinner() {
        if (board.generateLegalMoves(!blackPlaying).isEmpty()) {
            if (board.generateLegalMoves(blackPlaying).isEmpty()) {
                gameOver = true;
                if (board.getBlackScore() + board.getWhiteScore() > 0) {
                    return 1;
                } else if (board.getBlackScore() + board.getWhiteScore() < 0) {
                    return 2;
                } else {
                    return 3;
                }
            } else {
                return -1;
            }
        }
        return 0;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        if (blackPlaying) {
            System.out.println("Black is playing");
        } else {
            System.out.println("White is playing");
        }
        System.out.println("bScore: " + board.getBlackScore() + ", wScore: "
                + board.getWhiteScore());
        board.printBoard();
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */

    public void writeToFile() {
        try {
            new PrintWriter("saved_game.txt").close();
            PrintWriter p = new PrintWriter("saved_game.txt");
            if (gameOver) {
                p.println("y");
            } else {
                p.println("n");
            }

            if (blackPlaying) {
                p.println("y");
            } else {
                p.println("n");
            }

            if (blackIsBot) {
                p.println("y");
            } else {
                p.println("n");
            }

            if (whiteIsBot) {
                p.println("y");
            } else {
                p.println("n");
            }

            p.println(getBlackScore());
            p.println(getWhiteScore());

            for (int i = 0; i < boardSize(); i++) {
                String s = "";
                for (int j = 0; j < boardSize(); j++) {
                    s = s + getCell(new Position(i, j)) + "  ";
                }
                p.println(s);
            }

            p.close();
        } catch (IOException e) {
            System.out.println("Writing Error!");
        }
    }

    public void loadSavedGame() {
        try {
            FileReader r = new FileReader("saved_game.txt");
            BufferedReader b = new BufferedReader(r);
            boolean gO = false;
            boolean bP = false;
            boolean bB = false;
            boolean wB = false;
            int bS;
            int wS;
            int[][] brd = new int[boardSize()][boardSize()];
            String str = b.readLine();
            if (str.equals("y")) {
                gO = true;
            }
            str = b.readLine();
            if (str.equals("y")) {
                bP = true;
            }
            str = b.readLine();
            if (str.equals("y")) {
                bB = true;
            }
            str = b.readLine();
            if (str.equals("y")) {
                wB = true;
            }
            str = b.readLine();
            bS = Integer.parseInt(str);
            str = b.readLine();
            wS = -1 * Integer.parseInt(str);
            int index = 0;
            while ((str = b.readLine()) != null) {
                String[] nums = str.split("  ");
                if (nums.length != board.getBoardSize()) {
                    System.out.println("Saved board is a different size!");
                    return;
                }
                int[] line = new int[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    line[i] = Integer.parseInt(nums[i]);
                }
                brd[index] = line;
                index++;
            }
            board = new OthelloBoard(brd, bS, wS);
            gameOver = gO;
            blackPlaying = bP;
            blackIsBot = bB;
            whiteIsBot = wB;
            bot = new OthelloBot(6);
            b.close();
        } catch (IOException e) {
            System.out.println("Reading Error!");
        }
    }

    public void reset() {
        board = new OthelloBoard();
        blackPlaying = true;
        gameOver = false;
        whiteIsBot = false;
        blackIsBot = false;
        bot = new OthelloBot(6);
    }

    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     *
     * @return true if it's Player 1's turn,
     * false if it's Player 2's turn.
     */
    public boolean getCurrentPlayer() {
        return blackPlaying;
    }

    public int getBlackScore() {
        return board.getBlackScore();
    }

    public int getWhiteScore() {
        return -1 * board.getWhiteScore();
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     */
    public int getCell(Position pos) {
        return board.getCell(pos);
    }

    public int boardSize() {
        return board.getBoardSize();
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     * <p>
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     * <p>
     */
    public static void main(String[] args) {

    }
}
