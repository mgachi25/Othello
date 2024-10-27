package org.cis120.Othello;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TreeMap;
/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * <p>
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * <p>
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Othello oth; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 720;
    public static final int BOARD_HEIGHT = 720;

    private int cellX;
    private int cellY;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        oth = new Othello(); // initializes model for the game
        cellX = BOARD_WIDTH / oth.boardSize();
        cellY = BOARD_HEIGHT / oth.boardSize();
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (!oth.isBotPlaying()) {
                    Point p = e.getPoint();

                    // updates the model given the coordinates of the mouseclick
                    oth.playerTurn(new Position(p.y / cellY, p.x / cellX));

                }
                updateStatus(); // updates the status JLabel
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (oth.isBotPlaying()) {
                    oth.botTurn();
                }
                updateStatus(); // updates the status JLabel
                repaint();
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        oth.reset();
        status.setText("Black's Turn (Human) | Black Score: " + oth.getBlackScore()
                + " | White Score: " + oth.getWhiteScore());
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void writeToFile() {
        oth.writeToFile();
        requestFocusInWindow();
    }

    public void readFromFile() {
        oth.loadSavedGame();
        updateStatus();
        repaint();
        requestFocusInWindow();
    }

    public void toggleBlack() {
        oth.toggleBlackAI();
        updateStatus();
        requestFocusInWindow();
    }

    public void toggleWhite() {
        oth.toggleWhiteAI();
        updateStatus();
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (oth.isBotPlaying()) {
            if (oth.getCurrentPlayer()) {
                status.setText("Black's Turn (AI - press any key to play the move) | " +
                        "Black Score: " + oth.getBlackScore()
                        + " | White Score: " + oth.getWhiteScore());
            } else {
                status.setText("White's Turn (AI - press any key to play the move) | " +
                        "White Score: " + oth.getWhiteScore()
                        + " | Black Score: " + oth.getBlackScore());
            }
        } else {
            if (oth.getCurrentPlayer()) {
                status.setText("Black's Turn (Human) | Black Score: " + oth.getBlackScore()
                        + " | White Score: " + oth.getWhiteScore());
            } else {
                status.setText("White's Turn (Human) | White Score: " + oth.getWhiteScore()
                        + " | Black Score: " + oth.getBlackScore());
            }
        }


        int winner = oth.checkWinner();
        if (winner == 1) {
            status.setText("Black wins!!! | Black Score: " + oth.getBlackScore()
                    + " | White Score: " + oth.getWhiteScore());
        } else if (winner == 2) {
            status.setText("White wins!!!| White Score: " + oth.getWhiteScore()
                    + " | Black Score: " + oth.getBlackScore());
        } else if (winner == 3) {
            status.setText("It's a tie. Black Score: " + oth.getBlackScore()
                    + " | White Score: " + oth.getWhiteScore());
        }
    }

    /**
     * Draws the game board.
     * <p>
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.drawLine(cellX, 0, cellX, 800);
        g.drawLine(2 * cellX, 0, 2 * cellX, BOARD_HEIGHT);
        g.drawLine(3 * cellX, 0, 3 * cellX, BOARD_HEIGHT);
        g.drawLine(4 * cellX, 0, 4 * cellX, BOARD_HEIGHT);
        g.drawLine(5 * cellX, 0, 5 * cellX, BOARD_HEIGHT);
        g.drawLine(6 * cellX, 0, 6 * cellX, BOARD_HEIGHT);
        g.drawLine(7 * cellX, 0, 7 * cellX, BOARD_HEIGHT);
        g.drawLine(0, cellY, BOARD_WIDTH, cellY);
        g.drawLine(0, 2 * cellY, BOARD_WIDTH, 2 * cellY);
        g.drawLine(0, 3 * cellY, BOARD_WIDTH, 3 * cellY);
        g.drawLine(0, 4 * cellY, BOARD_WIDTH, 4 * cellY);
        g.drawLine(0, 5 * cellY, BOARD_WIDTH, 5 * cellY);
        g.drawLine(0, 6 * cellY, BOARD_WIDTH, 6 * cellY);
        g.drawLine(0, 7 * cellY, BOARD_WIDTH, 7 * cellY);

        // Draws X's and O's
        for (int i = 0; i < oth.boardSize(); i++) {
            for (int j = 0; j < oth.boardSize(); j++) {
                int state = oth.getCell(new Position(i, j));
                g.setColor(new Color(0, 153, 0));
                g.fillRect((cellX * j) + 1,
                        (cellY * i) + 1, cellX - 2, cellY - 2);
                if (state == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval((int) (0.3 * cellX) + cellX * j,
                            (int) (0.3 * cellY) + cellY * i, (int)
                                    (0.4 * cellX), (int) (0.4 * cellY));
                } else if (state == -1) {
                    g.setColor(Color.WHITE);
                    g.fillOval((int) (0.3 * cellX) + cellX * j,
                            (int) (0.3 * cellY) + cellY * i, (int)
                                    (0.4 * cellX), (int) (0.4 * cellY));
                }
            }
        }

        TreeMap<Position, OthelloBoard> moves = oth.getMoves();
        g.setColor(new Color(255, 150, 150));
        for (Position pos : moves.keySet()) {
            int row = pos.getRow();
            int col = pos.getCol();
            g.fillRect((cellX * col) + 1, (cellY * row) + 1, cellX - 2, cellY - 2);
            //g.fillOval(30 + cellX * col, 30 + cellY * row, 40, 40);
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
