package game;

import board.Board;
import board.BoardSetter;
import board.Position;
import piece.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        Board board = new Board();
        BoardSetter.setBoard(board);

        System.out.println("Welcome to chess!  We represent both rank and file as integers between 0 and 7 inclusive.\n" +
                "We interpret the row number to come first and the column number to come second.\n" +
                "For instance, entering a position of \"4 6\" will be interpreted as the square g5.\n");

        Color currentColor = Color.WHITE;
        while (!board.isCheckmated(currentColor) && !board.isStalemated(currentColor)) {
            takeTurn(board, currentColor);
            currentColor = currentColor.opposite();
        }

        System.out.println("Game over!");
    }

    private static void takeTurn(Board board, Color currentColor) {
        Position start = getPosition(currentColor.name() + " make your move.  " +
                "Enter start position as two integers between 0 and 7 inclusive with one space between them:");
        Position end = getPosition("Now enter the end position");

        if (!makeMove(board, currentColor, start, end)) {
            System.out.println("That's not a legal move.  Please try again.");
            takeTurn(board, currentColor);
        }
    }

    private static Position getPosition(String message) {
        try {
            System.out.println(message);
            return getPositionFromArray(bufferedReader.readLine().split(" "));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | IOException e) {
            System.out.println("I didn't understand that.  Please try again.");
            return getPosition(message);
        }
    }

    private static Position getPositionFromArray(String[] positionArray) {
        return new Position(Integer.valueOf(positionArray[0]), Integer.valueOf(positionArray[1]));
    }

    private static boolean makeMove(Board board, Color currentColor, Position start, Position end) {
        return board.getPieceAtPosition(start) != null
                && board.getPieceAtPosition(start).color == currentColor
                && board.movePiece(start, end);
    }
}