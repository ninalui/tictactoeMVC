package tictactoe;

/**
 * Run a TicTacToe game interactively on the console.
 */
public class Main {
    /**
     * Run a TicTacToe game interactively on the console.
     *
     * @param args not used
     */
    public static void main(String[] args) {
      TicTacToe m = new TicTacToeModel();
      TicTacToeView v = new SwingTicTacToeView("Tic-Tac-Toe");
      TicTacToeController c = new SwingTicTacToeController(v, m);
      c.playGame();
    }
  }

}
