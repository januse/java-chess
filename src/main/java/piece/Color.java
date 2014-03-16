package piece;

public enum Color {
    WHITE(0,1),
    BLACK(7,6);

    public int pieceRow;
    public int pawnRow;
    Color (int pieceRow, int pawnRow) {
        this.pieceRow = pieceRow;
        this.pawnRow = pawnRow;
    }

    public Color opposite() {
        if (this == WHITE) {
            return BLACK;
        }
        return WHITE;
    }

    public int directionOfTravel() {
        return pawnRow - pieceRow;
    }
}