### Othello Board and Adversary

## Overview

This project is an exploration of the Model-View-Controller framework to represent games. In this framework:

*   Model: This layer contains the game’s data, rules, and logic. It defines the state of the game, such as player stats, enemy positions, and inventory items. The model is independent of the user interface, focusing solely on the backend logic and data.

*   View: The view is responsible for rendering the game's graphics and displaying information to the player. It shows the current state of the model  and updates visually in response to changes in the model.

*   Controller: The controller handles input from the player and updates the model accordingly. It acts as the intermediary between the view and the model, ensuring inputs lead to appropriate changes in the game's state.

This framework is used to implement two mini-projects: (1) a simple implementation of John Conway's Game of Life and (2) an interface for the Othello game.
The latter of these is the main focus of the project. It allows players to play games, save game states using File I/O, and load old games.

In addition, I built an OthelloBot, which uses the minimax algorithm with Alpha/Beta pruning to optimally play the game. The user can toggle which side plays as AI, and can additionally make two bots play each other.

## Running the project

The game is run from src/main/Game.java. Use RunOthello or RunLife to run either project. This will launch a Java executable with instructions for playing the respective games. 

## Othello classes overview

Since the Othello project is more complex, here is an overview of each of the classes and their purpose:

*   The RunOthello class sets up widgets and controllers for the GUI and instantiates a GameBoard which will serve
  to contain the rest of the view/controller functionality of the game.

*   The GameBoard class instantiates an Othello object which acts as the game model. It also contains functionality
  for a repaint method and a status label (along with an updateStatus method) which keeps both the actual Othello
  board and the status label updated when changes happen to the game. This can occur when buttons initialized in the
  RunOthello class are pressed. There is also a mouseListener for mouse events (used to click squares when a human user
  is playing) and a keyListener that listens to key presses (this is a way for the user to control when AI moves are
  played, rather than just having the AI play moves instantly when it is activated).

*   The Othello class serves as the game model. The board is represented with an instance of the OthelloBoard object.
  This class also contains other objects relevant to gameplay such as a bot, instance variables to track the state of
  the game (gameOver, blackPlaying, etc.) These variables come with accessor and setter methods when necessary. The rest
  of the class is the methods relevant to gameplay. This includes playing a turn (human or bot), checking if the game
  is over and returning the winner, and writing/reading game data to a file when the user chooses to do so.

*   The OthelloBoard class represents the state of the game board at a given moment during gameplay. This includes board
  size, scores for each player, and the actual board itself. Within this class are the various methods necessary to
  ascertain which moves are legal and play them. Only playMove, isLegalMove, and generateLegalMoves are necessary to
  make moves in the Othello class, but the class contains helper methods to write these methods. playMove returns a
  new OthelloBoard so that generateLegalMoves can return a collection containing possible board states without changing
  the current state. These methods are in this class since all we need to know to perform these actions is the current
  state of the board.

*   The OthelloBot class describes a bot object that is an AI for othello. The only instance variable is depth which
  determines the depth of a recursive search for a move. The findMove method plays all possible first moves from a given
  position, and then uses the minimaxVal method to determine the value of each move before returning the best possible
  move. Both of these methods rely entirely on the TreeMap<Position, OthelloBoard> returned by generateLegalMoves in
  the OthelloBoard class

*   The Position class is a very simple class used to represent a row/col position on the board. It comes only with
  the necessary getters and setters. It is primarily useful for generateLegalMoves as it allows the position of the
  moves to be represented with a single object (rather than the row and column as separate params). Position implements
  Comparable so that it can be used as the key in a TreeMap.


## External Resources

*   I used the Othello Wikipedia page to consult the rules of the game: https://en.wikipedia.org/wiki/Reversi

*   While I didn't actively consult any resources to build my bot, I didn't invent the minimax algorithm either
    it is a rather classic algorithm for evaluating best-play in simple two-player games). I was first introduced to the
    algorithm in Sebastian Lague's "Coding Adventure: Chess AI" Youtube video, and built mine based on what I remembered
    from watching it. Here is the video: https://www.youtube.com/watch?v=U4ogK0MIzqk

