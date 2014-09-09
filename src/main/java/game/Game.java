package game;

import board.Board;
import board.BoardSetter;
import move.Move;
import piece.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    private static Player whitePlayer;
    private static Player blackPlayer;
    private static Board board;
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to chess!");

        createPlayers();
        setBoard();

        enterGameLoop();

        System.out.println("Game over!");
    }

    private static void enterGameLoop() {
        Player currentPlayer = whitePlayer;

        while (!board.isCheckmated(currentPlayer.color) && !board.isStalemated(currentPlayer.color) && board.moves.size() < 1000) {
            makeMove(currentPlayer, board);
            currentPlayer = board.getPlayerByColor(currentPlayer.getColor().opposite());
        }

        if (board.isCheckmated(currentPlayer.color)) {
            System.out.println(currentPlayer.getColor().opposite().name() + " checkmates " + currentPlayer.getColor().name());
        }
        else if (board.isStalemated(currentPlayer.color)) {
            System.out.println("The game ends in a stalemate");
        } else if (board.moves.size() >= 1000) {
            System.out.println("This madness has gone on long enough");
        }
    }

    private static boolean makeMove(Player player, Board board) {
        Move move = player.makeNextMove(board);
        if (board.getPieceAtPosition(move.getStartPosition()) != null
                && board.getPieceAtPosition(move.getStartPosition()).color == move.getPlayer().color
                && board.makeMove(move)) {
            System.out.println(player.getColor().name() + " has made move " + move.toString());
            return true;
        }
        System.out.println("That move is not legal. Please try again");
        return makeMove(player, board);
    }

    private static void createPlayers() {
        int numHumans = getNumberOfHumanPlayers();
        if (numHumans == 1) {
            Color humanPlayerColor = getHumanPlayerColor();
            whitePlayer = humanPlayerColor == Color.WHITE ? new HumanPlayer(Color.WHITE) : new RobotPlayer(Color.WHITE);
            blackPlayer = humanPlayerColor == Color.BLACK ? new HumanPlayer(Color.BLACK) : new RobotPlayer(Color.BLACK);
        } else if (numHumans == 0) {
            whitePlayer = new RobotPlayer(Color.WHITE);
            blackPlayer = new RobotPlayer(Color.BLACK);
        } else if(numHumans == 2) {
            whitePlayer = new HumanPlayer(Color.WHITE);
            blackPlayer = new HumanPlayer(Color.BLACK);
        } else {
            System.out.println("Please enter a number between 0 and 2 inclusive");
            createPlayers();
        }
    }

    private static int getNumberOfHumanPlayers() {
        try {
            System.out.println("How many humans are playing?");
            return Integer.parseInt(bufferedReader.readLine());
        } catch (Exception e) {
            System.out.println("I did not understand that.  Please enter 0, 1, or 2.");
            return getNumberOfHumanPlayers();
        }
    }

    private static Color getHumanPlayerColor() {
        try {
            System.out.println("What color would you like to play as?");
            return Color.valueOf(bufferedReader.readLine().toUpperCase());
        } catch (Exception e) {
            System.out.println("I did not understand that.  Please enter \"black\" or \"white\"");
            return getHumanPlayerColor();
        }
    }

    private static void setBoard() {
        board = new Board(whitePlayer, blackPlayer);
        BoardSetter.setBoard(board);
    }
}