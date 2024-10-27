package org.cis120.Life;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class instantiates the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself åand updates its status JLabel to
 * reflect the current state of the model.
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Life life; // model for the game
    private final JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 700;
    public static final int BOARD_HEIGHT = 700;

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

        life = new Life(); // initializes model for the game
        cellX = BOARD_WIDTH / life.boardSize();
        cellY = BOARD_HEIGHT / life.boardSize();
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (!life.isRunning()) {
                    Point p = e.getPoint();

                    // updates the model given the coordinates of the mouseclick
                    life.addCell(p.y / cellY, p.x / cellX);

                }
                updateStatus(); // updates the status JLabel
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (life.isRunning()) {
                    life.updateBoard();
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
        life.reset();
        status.setText("Choose starting cells");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void startRunning() {
        if(!life.isRunning()) {
            life.startRun();
        }
        updateStatus();
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (!life.isRunning()) {
            status.setText("Choose starting cells");
        } else {
            status.setText("Press/hold SPACE to run Life...");
        }

    }

    /**
     * Draws the game board.
     * <p>
     * All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        for (int i = 1; i < life.boardSize(); i++) {
            g.drawLine(i * cellX, 0, i * cellX, BOARD_HEIGHT);
        }

        for (int i = 1; i < life.boardSize(); i++) {
            g.drawLine(0, i * cellY, BOARD_WIDTH, i * cellY);
        }

        // Draws X's and O's
        for (int i = 0; i < life.boardSize(); i++) {
            for (int j = 0; j < life.boardSize(); j++) {
                int state = life.getCell(i,j);
                if (state == 1) {
                    g.setColor(Color.WHITE);
                    g.fillRect((cellX * j) + 1,
                            (cellY * i) + 1, cellX - 2, cellY - 2);
                } else if (state == 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect((cellX * j) + 1,
                            (cellY * i) + 1, cellX - 2, cellY - 2);
                }
            }
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


