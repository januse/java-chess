package board;

import game.Player;
import move.Move;
import move.MovePositions;
import piece.Color;
import piece.Piece;
import piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int BOARD_SIZE = 8;
    public final List<Move> moves = new ArrayList<Move>();
    private final Player playerOne;
    private final Player playerTwo;
    private Piece[][] squares = new Piece[BOARD_SIZE][BOARD_SIZE];


    public Board(Player playerOne, Player playerTwo) {
        if (playerOne.getColor() == playerTwo.getColor()) {
            throw new RuntimeException("Both players on this board have the same color!");
        }
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public boolean isCheckmated(Color color) {
        return isInCheck(color) && getAllLegalMovesByColor(color).isEmpty();
    }

    public boolean isStalemated(Color color) {
        return !isInCheck(color) && getAllLegalMovesByColor(color).isEmpty();
    }

    public void clearSquare(Position position) {
        squares[position.row][position.column] = null;
    }

    public void putPieceAtPosition(Piece piece, Position position) {
        squares[position.row][position.column] = piece;
    }

    public Piece getPieceAtPosition(Position position) {
        return squares[position.row][position.column];
    }

    public boolean makeMove(Move move) {
        if (move.isLegal()) {
            move.makeMove();
            moves.add(move);
            return true;
        }
        return false;
    }

    public boolean isInCheck(Color color) {
        Position kingPosition = getPositionOfKing(color);
        for (Move move : getAllPossibleMovesByColor(color.opposite())) {
            if (move.getEndPosition().equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayerByColor(Color color) {
        return playerOne.getColor() == color ? playerOne : playerTwo;
    }

    public List<Move> getAllLegalMovesByColor(Color color) {
        List<Move> moves = new ArrayList<>();
        for (Move move : getAllPossibleMovesByColor(color)) {
            if (move.isLegal()) {
                moves.add(move);
            }
        }
        return moves;
    }

    private List<Move> getAllPossibleMovesByColor(Color color) {
        List<Move> moves = new ArrayList<>();
        for (MovePositions movePositions : getAllPossibleMovePositions()) {
            if (getPieceAtPosition(movePositions.start) != null && getPieceAtPosition(movePositions.start).color == color) {
                Move move = new Move(movePositions, this, getPlayerByColor(color));
                if (move.isPossible()) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    private List<MovePositions> getAllPossibleMovePositions() {
        List<MovePositions> listOfMovePositions = new ArrayList<>();
        for (Position start : getAllPositionsOnBoard()) {
            for (Position end : getAllPositionsOnBoard()) {
                listOfMovePositions.add(new MovePositions(start, end));
            }
        }
        return listOfMovePositions;
    }

    private List<Position> getAllPositionsOnBoard() {
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                positions.add(new Position(i,j));
            }
        }
        return positions;
    }

    private Position getPositionOfKing(Color color) {
        for (Position position : getAllPositionsOnBoard()) {
            if (getPieceAtPosition(position) != null
                    && getPieceAtPosition(position).type == PieceType.KING
                    && getPieceAtPosition(position).color == color) {
                return position;
            }
        }
        throw new RuntimeException("A king of color " + color.name() + " is no longer on the board.");
    }
}