package move;

import board.Board;
import board.Position;
import piece.Color;
import piece.Piece;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Move {
    private Position startPosition;
    private Position endPosition;
    private Board board;
    private int rowDistance;
    private int columnDistance;
    private Piece movingPiece;
    private List<PieceManipulation> piecesManipulatedInMove = new ArrayList<PieceManipulation>();

    public Move(Position startPosition, Position endPosition, Board board) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.board = board;
        this.rowDistance = startPosition.row - endPosition.row;
        this.columnDistance = startPosition.column - endPosition.column;
        this.movingPiece = board.getPieceAtPosition(startPosition);
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void makeMove() {
        populateManipulatedPieces();
        manipulatePieces();
    }

    private void populateManipulatedPieces() {
        piecesManipulatedInMove.add(new PieceManipulation(startPosition, endPosition, movingPiece));
        if (board.getPieceAtPosition(endPosition) != null) {
            piecesManipulatedInMove.add(new PieceManipulation(endPosition, null, board.getPieceAtPosition(endPosition)));
        }
    }

    private void manipulatePieces() {
        // take off pieces first
        for (PieceManipulation manipulation : piecesManipulatedInMove) {
            if (manipulation.endPosition == null) {
                board.clearSquare(manipulation.endPosition);
            }
        }

        // if both pieces stay on board, order does not matter
        for (PieceManipulation manipulation : piecesManipulatedInMove) {
            board.clearSquare(manipulation.startPosition);
            board.putPieceAtPosition(manipulation.piece, manipulation.endPosition);
        }


        // put new pieces on last
        for (PieceManipulation manipulation : piecesManipulatedInMove) {
            if (manipulation.startPosition == null) {
                board.putPieceAtPosition(manipulation.piece, manipulation.endPosition);
            }
        }
    }

    public void makeInvertedMove() {
        invertManipulations();
        manipulatePieces();
        invertManipulations();
    }

    private void invertManipulations() {
        for (PieceManipulation manipulation : piecesManipulatedInMove) {
            Position temp = manipulation.startPosition;
            manipulation.startPosition = endPosition;
            manipulation.endPosition = temp;
        }
    }

    public boolean isLegal() {
        if (startPosition.equals(endPosition)) {
            return false;
        }

        if (movingPiece == null) {
            return false; // no piece at specified start position to move
        }

        Piece pieceToTake = board.getPieceAtPosition(endPosition);
        if (pieceToTake != null && pieceToTake.color == movingPiece.color) {
            return false; // cannot move somewhere where there's already a piece of your color
        }

        switch (movingPiece.type) {
            case KNIGHT:
                return isKnightMoveLegal();
            case ROOK:
                return isRookMoveLegal();
            case BISHOP:
                return isBishopMoveLegal();
            case QUEEN:
                return isQueenMoveLegal();
            case KING:
                return isKingMoveLegal();
            case PAWN:
                return isPawnMoveLegal();
        }

        throw new RuntimeException("The piece is of an invalid type.  It's not really possible, but whatever");
    }

    private boolean isKnightMoveLegal() {
        if ((abs(rowDistance) == 1 && abs(columnDistance) == 2) || (abs(rowDistance) == 2 && abs(columnDistance) == 1)) {
            return true;
        }
        return false; // If move is L-shaped and square is occupied by piece of different color, knight move is legal
    }

    private boolean isPawnMoveLegal() {
        if (rowDistance != 0 && columnDistance != 0 && abs(rowDistance) != abs(columnDistance)) {
            return false;
        }

        if (!movingPiece.hasMoved && (rowDistance == -2 && movingPiece.color == Color.WHITE || rowDistance == 2 && movingPiece.color == Color.BLACK)) { // hasn't moved as is two forward
            if (somethingIsInTheWay() || board.getPieceAtPosition(endPosition) != null) {
                return false;
            }
            return true;
        }

        if (rowDistance == -1 && movingPiece.color == Color.WHITE || rowDistance == 1 && movingPiece.color == Color.BLACK) {
            if (board.getPieceAtPosition(endPosition) != null) {
                return false;
            }
            return true;
        }

        if (abs(columnDistance) == 1 && abs(rowDistance) == 1) {
            if (board.getPieceAtPosition(endPosition) != null) {
                if (board.getPieceAtPosition(endPosition).color != movingPiece.color) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isKingMoveLegal() {
        if (abs(columnDistance) > 1 || abs(rowDistance) > 1) {
            return false;
        }

        return true;
    }

    private boolean isQueenMoveLegal() {
        if (rowDistance != 0 && columnDistance != 0 && abs(rowDistance) != abs(columnDistance)) {
            return false;
        }

        if (somethingIsInTheWay()) {
            return false;
        }

        else return true;
    }

    private boolean isBishopMoveLegal() {
        if (rowDistance != columnDistance) {
            return false;
        }

        if (somethingIsInTheWay()) {
            return false;
        }

        return true;
    }

    private boolean isRookMoveLegal() {
        if (rowDistance != 0 && columnDistance != 0) { // not moving in straight line
            return false;
        }

        if (somethingIsInTheWay()) {
            return false;
        }

        return true;
    }

    public boolean somethingIsInTheWay() {
        Position position = new Position(startPosition.row, startPosition.column);

        for (int i = 1; i < max(abs(rowDistance), abs(columnDistance)); i++) {
            setRowPosition(position, i);
            setColumnPosition(position, i);
            if (board.getPieceAtPosition(position) != null) {
                return true;
            }
        }

        return false;
    }

    private void setColumnPosition(Position position, int i) {
        if (columnDistance != 0) {
            if (columnDistance < 0) {
                position.column = startPosition.column + i;
            } else {
                position.column = startPosition.column - i;
            }
        }
    }

    private void setRowPosition(Position position, int i) {
        if (rowDistance != 0) {
            if (rowDistance < 0) {
                position.row = startPosition.row + i;
            } else {
                position.row = startPosition.row - i;
            }
        }
    }
}