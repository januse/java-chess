package move;

import board.Position;
import piece.Piece;

public class PieceManipulation {
    public Position startPosition;
    public Position endPosition;
    public Piece piece;
    public PieceManipulation(Position startPosition, Position endPosition, Piece piece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.piece = piece;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof PieceManipulation)) {
            return false;
        }
        PieceManipulation otherManipulation = (PieceManipulation) object;
        return ( (this.startPosition == null && otherManipulation.startPosition == null)
                || this.startPosition.equals(otherManipulation.startPosition))
                && ((this.endPosition == null && otherManipulation.endPosition == null)
                || this.endPosition.equals(otherManipulation.endPosition))
                && this.piece.equals(otherManipulation.piece);
    }

    @Override
    public int hashCode() {
        return (193 * ( 17 * (269 + startPosition.hashCode()) + endPosition.hashCode()) + piece.hashCode());
    }
}
