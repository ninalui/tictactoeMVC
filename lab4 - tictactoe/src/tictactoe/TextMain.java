package tictactoe;

import java.io.InputStreamReader;

/**
 * Run a Tic Tac Toe game interactively on the console.
 */
public class TextMain {
  /**
   * Run a Tic Tac Toe game interactively on the console.
   * @param args command-line arguments.
   */
  public static void main(String[] args) {
    new TicTacToeConsoleController(new InputStreamReader(System.in),
        System.out).playGame(new TicTacToeModel());
  }
}
