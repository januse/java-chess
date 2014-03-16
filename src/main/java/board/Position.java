package board;

import piece.Color;

public class Position {
    public int row;
    public int column;
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isKnightStartPosition() {
        return isOnPieceRow() && (column == 1 || column == 6);
    }

    public boolean isRookStartPosition() {
        return isOnPieceRow() && (column == 0 || column == 7);
    }

    public boolean isBishopStartPosition() {
        return isOnPieceRow() && (column == 2 || column == 5);
    }

    public boolean isQueenStartPosition() {
        return isOnPieceRow() && column == 3;
    }

    public boolean isKingStartPosition() {
        return isOnPieceRow() && column == 4;
    }

    public boolean isPawnStartPosition() {
        return row == Color.BLACK.pawnRow || row == Color.WHITE.pawnRow;
    }

    private boolean isOnPieceRow() {
        return row == Color.BLACK.pieceRow || row == Color.WHITE.pieceRow;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Position)) {
            return false;
        }
        Position otherPosition = (Position) object;
        return this.row == otherPosition.row
                && this.column == otherPosition.column;
    }

    @Override
    public int hashCode() {
        return (193 * (269 + row) + column);
    }
}