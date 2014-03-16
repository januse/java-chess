package board;

import move.Move;
import piece.Color;
import piece.Piece;
import piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int BOARD_SIZE = 8;
    private Piece[][] squares = new Piece[BOARD_SIZE][BOARD_SIZE];

    public boolean isCheckmated(Color color) {
        return isInCheck(color) && getAllLegalMovesByColor(color).isEmpty();
    }

    public boolean isStalemated(Color color) {
        return getAllLegalMovesByColor(color).isEmpty();
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

    public boolean movePiece(Position startPosition, Position endPosition) {
        Move move = new Move(startPosition, endPosition, this);
        if (move.isLegal()) {
            move.makeMove();
            return true;
        }
        return false;
    }

    public boolean isInCheck(Color color) {
        List<Move> opponentMoves = getAllPossibleMovesByColor(color.opposite());
        Position kingPosition = getPositionOfKing(color);
        for (Move move : opponentMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    public List<Move> getAllLegalMovesByColor(Color color) {
        List<Move> moves = new ArrayList<Move>();
        for (Move move : getAllPossibleMovesByColor(color)) {
            if (move.isLegal()) {
                moves.add(move);
            }
        }
        return moves;
    }

    private List<Move> getAllPossibleMovesByColor(Color color) {
        List<Move> moves = new ArrayList<Move>();
        for (Position startPosition : getAllPositionsOnBoard()) {
            for (Position endPosition : getAllPositionsOnBoard()) {
                if (getPieceAtPosition(startPosition) != null && getPieceAtPosition(startPosition).color == color) {
                    Move move = new Move(startPosition, endPosition, this);
                    if (move.isPossible()) {
                        moves.add(move);
                    }
                }
            }
        }
        return moves;
    }

    List<Position> getAllPositionsOnBoard() {
        List<Position> positions = new ArrayList<Position>();
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