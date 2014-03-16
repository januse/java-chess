import board.Board;
import board.BoardSetter;
import board.Position;
import piece.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    public static void main(String[] args) throws IOException {
        Board board = new Board();
        BoardSetter.setBoard(board);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        Position start;
        Position end;
        String startPosition;
        String endPosition;


        Color currentColor = Color.WHITE;
        while (!board.isCheckmated(currentColor) && !board.isStalemated(currentColor)) {
            System.out.println(currentColor.name() + " make your move.  Enter start position as two integers between 0 and 7 inclusive with one space between them:");
            startPosition = bufferedReader.readLine();

            System.out.println("Now enter the end position");
            endPosition = bufferedReader.readLine();

            String[] startPositionArray = startPosition.split(" ");
            String[] endPositionArray = endPosition.split(" ");
            start = getPositionFromArray(startPositionArray);
            end = getPositionFromArray(endPositionArray);

            boolean moveSuccessful;
            if (board.getPieceAtPosition(start) == null || board.getPieceAtPosition(start).color != currentColor) {
                moveSuccessful = false;
            } else {
                moveSuccessful = board.movePiece(start, end);
            }

            if (!moveSuccessful) {
                System.out.println("Can't do that");
            }


            if (moveSuccessful) {
                currentColor = currentColor.opposite();
            }
        }

        System.out.println("CHECKMATE");
    }

    private static Position getPositionFromArray(String[] positionArray) {
        return new Position(Integer.valueOf(positionArray[0]), Integer.valueOf(positionArray[1]));
    }
}