import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.Arrays;
import org.junit.Test;
import tictactoe.TicTacToe;
import tictactoe.TicTacToeConsoleController;
import tictactoe.TicTacToeController;
import tictactoe.TicTacToeModel;


/**
 * Test cases for the Tic Tac Toe controller, using mocks for readable and appendable.
 */
public class TicTacToeControllerTest {

  /**
   * Testing for a game with only one valid move then quitting.
   */
  @Test
  public void testSingleValidMove() {
    TicTacToe m = new TicTacToeModel();
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(new StringReader("2 2 q"), gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
                 + "-----------\n"
                 + "   |   |  \n"
                 + "-----------\n"
                 + "   |   |  \n"
                 + "Enter a move for X:\n"
                 + "   |   |  \n"
                 + "-----------\n"
                 + "   | X |  \n"
                 + "-----------\n"
                 + "   |   |  \n"
                 + "Enter a move for O:\n"
                 + "Game quit! Ending game state:\n"
                 + "   |   |  \n"
                 + "-----------\n"
                 + "   | X |  \n"
                 + "-----------\n"
                 + "   |   |  \n", gameLog.toString());
  }

  /**
   * Testing for entering a non-integer value for the row.
   */
  @Test
  public void testBogusInputAsRow() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("!#$ 2 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    // split the output into an array of lines
    String[] lines = gameLog.toString().split("\n");
    // check that it's the correct number of lines
    assertEquals(13, lines.length);
    // check error message correct
    assertEquals("Not a valid number: !#$", lines[lines.length - 7]);
    // check that the last 6 lines are correct
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals("Game quit! Ending game state:\n"
                 + "   |   |  \n"
                 + "-----------\n"
                 + "   |   |  \n"
                 + "-----------\n"
                 + "   |   |  ", lastMsg);
    // note no trailing \n here, because of the earlier split
  }

  /**
   * Testing for entering a non-integer value for the column.
   */
  @Test
  public void testBogusInputAsCol() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 abc 2 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(19, lines.length);
    assertEquals("Not a valid number: abc", lines[lines.length - 13]);
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals("Game quit! Ending game state:\n"
                 + "   | X |  \n"
                 + "-----------\n"
                 + "   |   |  \n"
                 + "-----------\n"
                 + "   |   |  ", lastMsg);
  }

  /**
   * Testing for a complete game with only valid inputs ending in a tie.
   */
  @Test
  public void testTieGame() {
    TicTacToe m = new TicTacToeModel();
    // note the entire sequence of user inputs for the entire game is in this one string:
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(60, lines.length);
    assertEquals("Game is over! Tie game.", lines[lines.length - 1]);
  }

  /**
   * Testing with a failing appendable.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = new FailingAppendable();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
  }

  /**
   * Testing for a complete game with only valid inputs ending with X winning.
   */
  @Test
  public void testCompleteGameWithWinnerX() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 3 1 1 2 1 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(36, lines.length);
    assertEquals("Game is over! X wins.", lines[lines.length - 1]);
  }

  /**
   * Testing for a complete game with only valid inputs ending with O winning.
   */
  @Test
  public void testCompleteGameWithWinnerO() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 2 3 1 2 3 3 1 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(42, lines.length);
    assertEquals("Game is over! O wins.", lines[lines.length - 1]);
  }

  /**
   * Testing for quitting on the row input.
   */
  // Input where the q comes instead of an integer for the row
  @Test
  public void testQuitOnRow() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 2 2 1 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(24, lines.length);
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals("Game quit! Ending game state:\n"
                 + "   | X |  \n"
                 + "-----------\n"
                 + " O |   |  \n"
                 + "-----------\n"
                 + "   |   |  ", lastMsg);
  }

  /**
   * Testing for quitting on the column input.
   */
  @Test
  public void testQuitOnCol() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 2 2 3 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(24, lines.length);
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals("Game quit! Ending game state:\n"
                 + " X |   |  \n"
                 + "-----------\n"
                 + "   | O |  \n"
                 + "-----------\n"
                 + "   |   |  ", lastMsg);
  }

  /**
   * Testing for valid integer input but outside the bounds of the board for the row.
   */
  @Test
  public void testRowValidInputButOutOfBounds() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 4 2 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(19, lines.length);
    assertEquals("Not a valid move: 4, 2", lines[lines.length - 7]);
  }

  /**
   * Testing for valid integer input but outside the bounds of the board for the column.
   */
  @Test
  public void testColValidInputButOutOfBounds() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 2 4 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(19, lines.length);
    assertEquals("Not a valid move: 2, 4", lines[lines.length - 7]);
  }

  /**
   * Testing for valid integer input but the cell is already occupied.
   */
  @Test
  public void testValidInputButCellOccupied() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 1 1 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(19, lines.length);
    assertEquals("Not a valid move: 1, 1", lines[lines.length - 7]);
  }

  /**
   * Testing for multiple invalid moves in a row of various kinds.
   * Order of input: valid move, occupied, valid move, invalid input for row, valid input for row,
   * invalid input for column, out of bounds on column, out of bounds on row, both out of bounds,
   * valid move, quit.
   */
  @Test
  public void testMultipleInvalidMoves() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 1 1 2 1 abc 3 3.9 4 3 10 10 2 6 10 3 3 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);

    String[] lines = gameLog.toString().split("\n");
    assertEquals(37, lines.length);
    assertEquals("Not a valid move: 1, 1", lines[lines.length - 25]);
    assertEquals("Not a valid number: abc", lines[lines.length - 18]);
    assertEquals("Not a valid number: 3.9", lines[lines.length - 17]);
    assertEquals("Not a valid move: 3, 4", lines[lines.length - 16]);
    assertEquals("Not a valid move: 3, 10", lines[lines.length - 15]);
    assertEquals("Not a valid move: 10, 2", lines[lines.length - 14]);
    assertEquals("Not a valid move: 6, 10", lines[lines.length - 13]);

    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals("Game quit! Ending game state:\n"
                 + " X |   |  \n"
                 + "-----------\n"
                 + " O |   |  \n"
                 + "-----------\n"
                 + "   |   | X", lastMsg);
  }

  /**
   * Testing for a complete game ending in a tie with a mix of valid and invalid moves.
   */
  @Test
  public void testValidAndInvalidMovesTieGame() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 3 3 2 2 1 2 1 3 a 2 3 2 % 1 6 6 3 1 3 2");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);

    String[] lines = gameLog.toString().split("\n");
    assertEquals(64, lines.length);
    assertEquals("Not a valid move: 2, 2", lines[lines.length - 40]);
    assertEquals("Not a valid number: a", lines[lines.length - 27]);
    assertEquals("Not a valid number: %", lines[lines.length - 20]);
    assertEquals("Not a valid move: 6, 6", lines[lines.length - 13]);
    assertEquals("Game is over! Tie game.", lines[lines.length - 1]);
  }

  /**
   * Testing for a complete game ending with X winning with a mix of valid and invalid moves.
   */
  @Test
  public void testValidAndInvalidMovesWinnerX() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 2 1 2 2 / 1 d 3 4 4 1 1 4 2 2 4 3 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);

    String[] lines = gameLog.toString().split("\n");
    assertEquals(42, lines.length);
    assertEquals("Not a valid number: /", lines[lines.length - 18]);
    assertEquals("Not a valid number: d", lines[lines.length - 17]);
    assertEquals("Not a valid move: 4, 4", lines[lines.length - 10]);
    assertEquals("Not a valid move: 1, 1", lines[lines.length - 9]);
    assertEquals("Not a valid move: 4, 2", lines[lines.length - 8]);
    assertEquals("Not a valid move: 2, 4", lines[lines.length - 7]);
    assertEquals("Game is over! X wins.", lines[lines.length - 1]);
  }

  /**
   * Testing for a complete game ending with O winning with a mix of valid and invalid moves.
   */
  @Test
  public void testValidAndInvalidMovesWinnerO() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 $ 1 2 3 2 3 a 1 2 1 3 2 2 8 9 3 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);

    String[] lines = gameLog.toString().split("\n");
    assertEquals(46, lines.length);
    assertEquals("Not a valid number: $", lines[lines.length - 40]);
    assertEquals("Not a valid move: 2, 3", lines[lines.length - 27]);
    assertEquals("Not a valid number: a", lines[lines.length - 26]);
    assertEquals("Not a valid move: 8, 9", lines[lines.length - 7]);
    assertEquals("Game is over! O wins.", lines[lines.length - 1]);
  }

  /**
   * Testing that an exception is thrown when there is no more input but the game is not over.
   */
  @Test(expected = IllegalStateException.class)
  public void testNoMoreInputs() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
  }

  /**
   * Testing for when the model is null, an exception is thrown from playGame().
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    TicTacToe m = null;
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
  }

  /**
   * Testing for when the appendable is null, an exception is thrown from the constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = null;
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
  }

  /**
   * Testing for when the readable is null, an exception is thrown from the constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = null;
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
  }
}
