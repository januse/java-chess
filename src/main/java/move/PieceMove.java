package move;

import board.Position;
import piece.Piece;

public class PieceMove {
    public Position startPosition;
    public Position endPosition;
    public Piece piece;
    public PieceMove(Position startPosition, Position endPosition, Piece piece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.piece = piece;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof PieceMove)) {
            return false;
        }
        PieceMove otherPieceMove = (PieceMove) object;
        return ( (this.startPosition == null && otherPieceMove.startPosition == null)
                || this.startPosition.equals(otherPieceMove.startPosition))
                && ((this.endPosition == null && otherPieceMove.endPosition == null)
                || this.endPosition.equals(otherPieceMove.endPosition))
                && this.piece.equals(otherPieceMove.piece);
    }

    @Override
    public int hashCode() {
        return (193 * ( 17 * (269 + startPosition.hashCode()) + endPosition.hashCode()) + piece.hashCode());
    }
}
