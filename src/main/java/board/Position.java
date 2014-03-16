package board;

public class Position {
    public static final int QUEEN_SIDE_ROOK_COLUMN = 0;
    public static final int QUEEN_SIDE_KNIGHT_COLUMN = 1;
    public static final int QUEEN_SIDE_BISHOP_COLUMN = 2;
    public static final int QUEEN_COLUMN = 3;
    public static final int KING_COLUMN = 4;
    public static final int KING_SIDE_BISHOP_COLUMN = 5;
    public static final int KING_SIDE_KNIGHT_COLUMN = 6;
    public static final int KING_SIDE_ROOK_COLUMN = 7;

    public int row;
    public int column;
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
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