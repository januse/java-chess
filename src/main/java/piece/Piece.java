package piece;

public class Piece {
    public PieceType type;
    public Color color;
    public boolean hasMoved = false;
    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }
}