package tictactoe;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class represents a controller for a Tic Tac Toe game. It takes in a Readable object to read
 * input from and an Appendable object to write output to. It prompts the user for input and
 * validates it, then passes it to the model to make a move. It also displays the current state of
 * the game to the user. It will continue to prompt the user for input until the game is over or
 * the user quits. When the game is over, it will display the final state of the game and the
 * winner. If the user quits, it will display the end state of the game and the game ends.
 */
public class TicTacToeConsoleController implements TicTacToeController {
  private final Readable in;
  private final Appendable out;

  /**
   * Constructor for TicTacToeConsoleController. Initializes the Readable and Appendable objects
   * that will be used to read input and write output.
   *
   * @param in  the Readable object to read input from.
   * @param out the Appendable object to write output to.
   * @throws IllegalArgumentException if either in or out is null.
   */
  public TicTacToeConsoleController(Readable in, Appendable out) throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable or Appendable cannot be null.");
    }
    this.in = in;
    this.out = out;
  }

  @Override
  public void playGame(TicTacToe m) {
    if (m == null) {
      throw new IllegalArgumentException("There is no model.");
    }

    Scanner scan = new Scanner(this.in);
    Integer row = null;
    Integer col = null;
    int moveInput;
    boolean prompt = true;
    boolean gameQuit = false;
    String input = "";

    try {
      while (!m.isGameOver()) {
        if (prompt) {
          this.out.append(m.toString() + "\n");
          this.out.append("Enter a move for " + m.getTurn().toString() + ":\n");
          prompt = false;
        }

        input = scan.next();
        if ("q".equalsIgnoreCase(input)) {
          gameQuit = true;
          break;
        }

        try {
          moveInput = Integer.parseInt(input);
          if (row == null) {
            row = moveInput;
          } else {
            col = moveInput;
            m.move(row - 1, col - 1);
            prompt = true;
            row = col = null;
          }
        } catch (NumberFormatException e) {
          this.out.append("Not a valid number: " + input + "\n");
        } catch (IllegalArgumentException e) {
          this.out.append("Not a valid move: " + row + ", " + col + "\n");
          row = col = null;
        }
      }

      if (m.isGameOver()) {
        this.out.append(m.toString());
        this.out.append("\nGame is over! ");
        if (m.getWinner() == null) {
          this.out.append("Tie game.");
        } else {
          this.out.append(m.getWinner() + " wins.");
        }
      } else if (gameQuit) {
        this.out.append("Game quit! Ending game state:\n" + m.toString() + "\n");
      }

    } catch (IOException e) {
      throw new IllegalStateException("Append failed.");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("No input.");
    }
    scan.close();
  }
}


