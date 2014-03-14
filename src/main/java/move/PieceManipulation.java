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
}
