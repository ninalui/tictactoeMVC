package tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class represents a single game of Tic Tac Toe, played on a standard three-by-three grid
 * with two players. The class provides all relevant functionality for playing the game, including
 * the ability to make moves, check if the game is over, who won the game if there is a winner,
 * and provides the current state of the game.
 */
public class TicTacToeModel implements TicTacToe {
  private Player[][] board;
  private Player turn;
  private Player winner;

  /**
   * Constructor to create a new Tic Tac Toe game. It initializes an empty game board and
   * sets initial values for turn and winner.
   */
  public TicTacToeModel() {
    this.board = new Player[3][3];
    for (int row = 0; row < 3; row++) {
      Arrays.fill(this.board[row], null);
    }
    this.turn = Player.X;
    this.winner = null;
  }

  @Override
  public void move(int r, int c) {
    if (this.isGameOver()) {
      throw new IllegalStateException("The game is over!");
    }
    if (this.checkOutOfBounds(r, c)) {
      throw new IllegalArgumentException("Chosen position is out of bounds.");
    }
    if (this.getMarkAt(r, c) != null) {
      throw new IllegalArgumentException("The chosen position is occupied.");
    }

    this.board[r][c] = this.getTurn();

    if (this.getTurn() == Player.X) {
      this.turn = Player.O;
    } else {
      this.turn = Player.X;
    }
  }

  @Override
  public Player getTurn() {
    return this.turn;
  }

  @Override
  public boolean isGameOver() {
    if (checkWin()) {
      return true;
    }

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (this.getMarkAt(row, col) == null) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public Player getWinner() {
    return this.winner;
  }

  @Override
  public Player[][] getBoard() {
    return Arrays.stream(this.board)
        .map(Player[]::clone)
        .toArray(Player[][]::new);
  }

  @Override
  public Player getMarkAt(int r, int c) throws IllegalArgumentException {
    if (this.checkOutOfBounds(r, c)) {
      throw new IllegalArgumentException("Invalid row or column.");
    }
    return this.board[r][c];
  }

  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(getBoard()).map(
      row -> " " + Arrays.stream(row).map(
        p -> p == null ? " " : p.toString()).collect(Collectors.joining(" | ")))
          .collect(Collectors.joining("\n-----------\n"));
    // This is the equivalent code as above, but using iteration, and still using 
    // the helpful built-in String.join method.
    /*
    List<String> rows = new ArrayList<>();
    for(Player[] row : getBoard()) {
      List<String> rowStrings = new ArrayList<>();
      for(Player p : row) {
        if(p == null) {
          rowStrings.add(" ");
        } else {
          rowStrings.add(p.toString());
        }
      }
      rows.add(" " + String.join(" | ", rowStrings));
    }
    return String.join("\n-----------\n", rows);
    */
  }

  /**
   * Checks if the given row and column are outside the bounds of the Tic Tac Toe game board.
   *
   * @param row the row to check.
   * @param col the column to check.
   * @return true if the given row and column are out of bounds, false otherwise.
   */
  private boolean checkOutOfBounds(int row, int col) {
    return row < 0 || row >= 3 || col < 0 || col >= 3;
  }

  /**
   * Checks if a player has won the game of Tic Tac Toe.
   * @return true if there is a winner, false otherwise.
   */
  private boolean checkWin() {
    for (int row = 0; row < 3; row++) {
      if (this.board[row][0] != null && this.board[row][0] == this.board[row][1]
          && this.board[row][1] == this.board[row][2]) {
        this.winner = this.getMarkAt(row, 0);
        return true;
      }
    }
    for (int col = 0; col < 3; col++) {
      if (this.board[0][col] != null && this.board[0][col] == this.board[1][col]
          && this.board[1][col] == this.board[2][col]) {
        this.winner = this.getMarkAt(0, col);
        return true;
      }
    }
    if (this.board[0][0] != null && this.board[0][0] == this.board[1][1]
        && this.board[1][1] == this.board[2][2]) {
      this.winner = this.getMarkAt(0, 0);
      return true;
    }
    if (this.board[0][2] != null && this.board[0][2] == this.board[1][1]
        && this.board[1][1] == this.board[2][0]) {
      this.winner = this.getMarkAt(0, 2);
      return true;
    }
    return false;
  }
}
