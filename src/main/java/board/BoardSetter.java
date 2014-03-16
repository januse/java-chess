package board;

import piece.Color;
import piece.Piece;
import piece.PieceType;

import static piece.Color.*;

public class BoardSetter {

    public static void setBoard(Board board) {
        int[] rowsWithPieces = new int[]{BLACK.pieceRow, WHITE.pieceRow, BLACK.pawnRow, WHITE.pawnRow};

        for (int row : rowsWithPieces) {
            for (int column = 0; column < Board.BOARD_SIZE; column++) {
                setOriginalPieceAtPosition(new Position(row, column), board);
            }
        }
    }

    private static void setOriginalPieceAtPosition(Position position, Board board) {
        Color color = getColor(position);

        if (position.row == WHITE.pawnRow || position.row == BLACK.pawnRow) {
            board.putPieceAtPosition(new Piece(color, PieceType.PAWN), position);
        } else {
            switch (position.column) {
                case Position.QUEEN_SIDE_ROOK_COLUMN:
                case Position.KING_SIDE_ROOK_COLUMN:
                    board.putPieceAtPosition(new Piece(color, PieceType.ROOK), position);
                    break;
                case Position.QUEEN_SIDE_BISHOP_COLUMN:
                case Position.KING_SIDE_BISHOP_COLUMN:
                    board.putPieceAtPosition(new Piece(color, PieceType.BISHOP), position);
                    break;
                case Position.QUEEN_SIDE_KNIGHT_COLUMN:
                case Position.KING_SIDE_KNIGHT_COLUMN:
                    board.putPieceAtPosition(new Piece(color, PieceType.KNIGHT), position);
                    break;
                case Position.QUEEN_COLUMN:
                    board.putPieceAtPosition(new Piece(color, PieceType.QUEEN), position);
                    break;
                case Position.KING_COLUMN:
                    board.putPieceAtPosition(new Piece(color, PieceType.KING), position);
                    break;
            }
        }
    }


    private static Color getColor(Position position) {
        Color color;

        if (position.row == WHITE.pawnRow || position.row == WHITE.pieceRow) {
            color = WHITE;
        } else {
            color = BLACK;
        }

        return color;
    }
}
