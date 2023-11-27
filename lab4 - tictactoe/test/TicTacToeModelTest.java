import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import tictactoe.Player;
import tictactoe.TicTacToe;
import tictactoe.TicTacToeModel;

/**
 * Test cases for the Tic Tac Toe model. Verifying that game state is properly managed, and
 * all game actions are properly validated.
 */
public class TicTacToeModelTest {

  private TicTacToe ttt1 = new TicTacToeModel();

  /**
   * Test the move() method works as expected.
   */
  @Test
  public void testMove() {
    ttt1.move(0, 0);
    assertEquals(Player.X, ttt1.getMarkAt(0, 0));
    assertEquals(Player.O, ttt1.getTurn());
  }

  /**
   * Test the model works as expected when the game is won on a row.
   */
  @Test
  public void testHorizontalWin() {
    ttt1.move(0, 0); // X takes upper left
    assertFalse(ttt1.isGameOver());
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(0, 1); // X takes upper middle
    assertNull(ttt1.getWinner());
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(0, 2); // X takes upper right
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.X, ttt1.getWinner());
    assertEquals(" X | X | X\n"
        + "-----------\n"
        + " O |   |  \n"
        + "-----------\n"
        + " O |   |  ", ttt1.toString());
  }

  /**
   * Test the model works as expected when the game is won on a diagonal.
   */
  @Test
  public void testDiagonalWin() {
    diagonalWinHelper();
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.O, ttt1.getWinner());
    assertEquals(" X | X | O\n"
        + "-----------\n"
        + " X | O |  \n"
        + "-----------\n"
        + " O |   |  ", ttt1.toString());
  }

  /**
   * Setting up a situation where the game is over, O wins on the diagonal, board is not full.
   */
  private void diagonalWinHelper() {
    ttt1.move(0, 0); // X takes upper left
    assertFalse(ttt1.isGameOver());
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(1, 0); // X takes middle left
    assertNull(ttt1.getWinner());
    ttt1.move(1, 1); // O takes center
    ttt1.move(0, 1); // X takes upper middle
    ttt1.move(0, 2); // O takes upper right
  }

  /**
   * Test that exceptions are thrown when moving to an occupied position or out of bounds.
   */
  @Test
  public void testInvalidMove() {
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
    assertEquals(Player.X, ttt1.getMarkAt(0, 0));
    try {
      ttt1.move(0, 0);
      fail("Invalid move should have thrown exception");
    } catch (IllegalArgumentException iae) {
      //assertEquals("Position occupied", iae.getMessage());
      assertTrue(iae.getMessage().length() > 0);
    }
    try {
      ttt1.move(-1, 0);
      fail("Invalid move should have thrown exception");
    } catch (IllegalArgumentException iae) {
      //assertEquals("Position occupied", iae.getMessage());
      assertTrue(iae.getMessage().length() > 0);
    }
  }

  /**
   * Test that an exception is thrown when attempting to move after the game is over.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveAttemptAfterGameOver() {
    diagonalWinHelper();
    ttt1.move(2, 2); // 2,2 is an empty position
  }

  /**
   * Test the model works as expected for a game ending in a tie.
   */
  @Test
  public void testCatsGame() {
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
    ttt1.move(1, 1);
    assertEquals(Player.X, ttt1.getTurn());
    ttt1.move(0, 2);
    ttt1.move(0, 1);
    ttt1.move(2, 1);
    ttt1.move(1, 0);
    ttt1.move(1, 2);
    ttt1.move(2, 2);
    ttt1.move(2, 0);
    assertTrue(ttt1.isGameOver());
    assertNull(ttt1.getWinner());
    assertEquals(" X | O | X\n"
        + "-----------\n"
        + " O | O | X\n"
        + "-----------\n"
        + " X | X | O", ttt1.toString());
  }

  /**
   * Test that an exception is thrown when trying to get the mark at a row that is out of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetMarkAtRow() {
    ttt1.getMarkAt(-12, 0);
  }

  /**
   * Test that an exception is thrown when trying to get the mark at a column is out of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetMarkAtCol() {
    ttt1.getMarkAt(0, -30);
  }

  /**
   * Test that the board returned by getBoard() is a copy of the board, not the actual board.
   */
  @Test
  public void testGetBoard() {
    diagonalWinHelper();
    Player[][] bd = ttt1.getBoard();
    assertEquals(Player.X, bd[0][0]);
    assertEquals(Player.O, bd[1][1]);
    assertEquals(Player.X, bd[0][1]);

    // attempt to cheat by mutating board returned by getBoard()
    // check correct preconditions
    assertEquals(Player.O, bd[2][0]);
    assertEquals(Player.O, ttt1.getMarkAt(2, 0));
    bd[2][0] = Player.X;  // mutate
    // check correct post conditions
    assertEquals(Player.O, ttt1.getMarkAt(2, 0));
    Player[][] bd2 = ttt1.getBoard();
    assertEquals(Player.O, bd2[2][0]);
  }

  /**
   * Test that the initial state of the game is as expected.
   */
  @Test
  public void testInitialState() {
    assertNull(ttt1.getWinner());
    assertFalse(ttt1.isGameOver());
    assertEquals(Player.X, ttt1.getTurn());
    assertNull(ttt1.getMarkAt(0, 0));
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  ", ttt1.toString());
  }

  /**
   * Test that getTurn() begins with X and alternates between players as expected.
   */
  @Test
  public void testGetTurn() {
    assertEquals(Player.X, ttt1.getTurn());
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
    ttt1.move(1, 1);
    assertEquals(Player.X, ttt1.getTurn());
  }

  /**
   * Test that getMarkAt() returns the correct value.
   */
  @Test
  public void testGetMarkAt() {
    ttt1.move(0, 0);
    assertEquals(Player.X, ttt1.getMarkAt(0, 0));
    ttt1.move(2, 2);
    assertEquals(Player.O, ttt1.getMarkAt(2, 2));
  }

  /**
   * Test that isGameOver() works as expected.
   */
  @Test
  public void testIsGameOver() {
    assertFalse(ttt1.isGameOver());
    ttt1.move(0, 0);
    ttt1.move(1, 1);
    assertFalse(ttt1.isGameOver());
    ttt1.move(0, 1);
    ttt1.move(1, 2);
    ttt1.move(0, 2);
    assertTrue(ttt1.isGameOver());
  }

  /**
   * Test the model works as expected when the board is full and there is a winner.
   */
  @Test
  public void testFullWinGame() {
    ttt1.move(0, 0);
    ttt1.move(1, 2);
    ttt1.move(0, 2);
    ttt1.move(0, 1);
    ttt1.move(2, 1);
    ttt1.move(1, 0);
    ttt1.move(1, 1);
    ttt1.move(2, 0);
    ttt1.move(2, 2);
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.X, ttt1.getWinner());
    assertEquals(" X | O | X\n"
        + "-----------\n"
        + " O | X | O\n"
        + "-----------\n"
        + " O | X | X", ttt1.toString());
  }

  /**
   * Test the model works as expected when the game is won on a column.
   */
  @Test
  public void testColumnWin() {
    ttt1.move(0, 2);
    ttt1.move(1, 1);
    ttt1.move(0, 0);
    ttt1.move(0, 1);
    ttt1.move(2, 2);
    ttt1.move(2, 1);
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.O, ttt1.getWinner());
  }
}
