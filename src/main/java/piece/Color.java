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
}