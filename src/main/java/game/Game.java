package game;

import board.Board;
import board.BoardSetter;
import move.Move;
import piece.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        Player whitePlayer = new HumanPlayer(Color.WHITE);
        Player blackPlayer = new HumanPlayer(Color.BLACK);
        Board board = new Board(whitePlayer, blackPlayer);
        BoardSetter.setBoard(board);

        System.out.println("Welcome to chess!  We represent both rank and file as integers between 0 and 7 inclusive.\n" +
                "We interpret the row number to come first and the column number to come second.\n" +
                "For instance, entering a position of \"4 6\" will be interpreted as the square g5.\n");

        Player currentPlayer = whitePlayer;
        while (!board.isCheckmated(currentPlayer.color) && !board.isStalemated(currentPlayer.color)) {
            makeMove(currentPlayer, board);
            currentPlayer = board.getPlayerByColor(currentPlayer.getColor().opposite());
        }

        System.out.println("Game over!");
    }

    private static boolean makeMove(Player player, Board board) {
        Move move = player.makeNextMove(board);
        if (board.getPieceAtPosition(move.getStartPosition()) != null
                && board.getPieceAtPosition(move.getStartPosition()).color == move.getPlayer().color
                && board.makeMove(move)) {
            return true;
        }
        System.out.println("That move is not legal. Please try again");
        return makeMove(player, board);
    }
}