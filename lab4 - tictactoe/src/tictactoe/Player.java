package tictactoe;

/**
 * Enum to represent the two players in a game of Tic Tac Toe.
 */
public enum Player {
  X("X"),
  O("O");

  private final String display;

  /**
   * Display the player's symbol.
   * @param display the String that will be displayed.
   */
  Player(String display) {
    this.display = display;
  }

  @Override
  public String toString() {
    return this.display;
  }
}

