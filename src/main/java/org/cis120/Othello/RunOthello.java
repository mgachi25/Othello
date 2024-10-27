package org.cis120.Othello;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 */
public class RunOthello implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("OTHELLO");
        frame.setLocation(800, 800);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        final JFrame jFrame = new JFrame("Info");

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton info = new JButton("Info");
        info.addActionListener(e ->
                JOptionPane.showMessageDialog(jFrame, "Welcome to Othello! The rules are " +
                        "as follows:\n \n" +
                        "-Black plays first, valid playing squares " +
                        "are highlighted in pink\n" +
                        "-A valid move surrounds a line (any direction) of " +
                        "opponent pieces with your pieces\n" +
                        "-After a valid move is played, all opponent pieces on " +
                        "the line change color\n" +
                        "-If a player has no valid moves, their turn is skipped\n" +
                        "-The game is over when neither player can make a move\n" +
                        "-Whichever player has the most pieces wins\n \n" +
                        "This game has the both players set as humans by default.\n" +
                        "Use the toggle buttons to decide whether each " +
                        "player is a human or AI.\n" +
                        "The status bar at the bottom will tell you which " +
                        "(human/AI) is enabled, as well " +
                        "as the current score.\n" +
                        "Save your game, load a previous one, or reset the " +
                        "game using the buttons at the top"));
        control_panel.add(info);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton saveGame = new JButton("Save Game");
        saveGame.addActionListener(e -> board.writeToFile());
        control_panel.add(saveGame);

        final JButton loadSavedGame = new JButton("Load Game");
        loadSavedGame.addActionListener(e -> board.readFromFile());
        control_panel.add(loadSavedGame);

        final JButton toggleBlack = new JButton("Toggle Black AI");
        toggleBlack.addActionListener(e -> board.toggleBlack());
        control_panel.add(toggleBlack);

        final JButton toggleWhite = new JButton("Toggle White AI");
        toggleWhite.addActionListener(e -> board.toggleWhite());
        control_panel.add(toggleWhite);


        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
        JOptionPane.showMessageDialog(jFrame, "Welcome to Othello! The rules are " +
                "as follows:\n \n" +
                "-Black plays first, valid playing squares " +
                "are highlighted in pink\n" +
                "-A valid move surrounds a line (any direction) of " +
                "opponent pieces with your pieces\n" +
                "-After a valid move is played, all opponent pieces on " +
                "the line change color\n" +
                "-If a player has no valid moves, their turn is skipped\n" +
                "-The game is over when neither player can make a move\n" +
                "-Whichever player has the most pieces wins\n \n" +
                "This game has the both players set as humans by default.\n" +
                "Use the toggle buttons to decide whether each " +
                "player is a human or AI.\n" +
                "The status bar at the bottom will tell you which " +
                "(human/AI) is enabled, as well " +
                "as the current score.\n" +
                "Save your game, load a previous one, or reset the " +
                "game using the buttons at the top");
    }
}