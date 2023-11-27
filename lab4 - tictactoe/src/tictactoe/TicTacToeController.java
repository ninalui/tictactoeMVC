package tictactoe;

/**
 * Represents a Controller for Tic Tac Toe: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface TicTacToeController {

  /**
   * Execute a single game of Tic Tac Toe given a Tic Tac Toe Model. When the game is over,
   * the playGame method ends.
   *
   * @param m a non-null Tic Tac Toe Model
   */
  void playGame(TicTacToe m);
}
