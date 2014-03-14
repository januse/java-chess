package board;

import piece.Color;
import piece.Piece;
import piece.PieceType;

public class BoardSetter {

    public static void setBoard(Board board) {
        for (Position position : board.getAllPositionsOnBoard()) {
            setOriginalPieceAtPosition(position, board);
        }
    }

    private static void setOriginalPieceAtPosition(Position position, Board board) {
        Color color = setColor(position);

        if (color == null) {
            return;
        }

        if (position.isPawnStartPosition()) {
            board.putPieceAtPosition(new Piece(PieceType.PAWN, color), position);
        }

        else if (position.isRookStartPosition()) {
            board.putPieceAtPosition(new Piece(PieceType.ROOK, color), position);
        }

        else if (position.isKnightStartPosition()) {
            board.putPieceAtPosition(new Piece(PieceType.KNIGHT, color), position);
        }

        else if (position.isBishopStartPosition()) {
            board.putPieceAtPosition(new Piece(PieceType.BISHOP, color), position);
        }

        else if (position.isQueenStartPosition()) {
            board.putPieceAtPosition(new Piece(PieceType.QUEEN, color), position);
        }

        else if (position.isKingStartPosition()) {
            board.putPieceAtPosition(new Piece(PieceType.KING, color), position);
        }
    }

    private static Color setColor(Position position) {
        Color color;

        if (position.row == Color.WHITE.pawnRow || position.row == Color.WHITE.pieceRow) {
            color = Color.WHITE;
        } else if (position.row == Color.BLACK.pawnRow || position.row == Color.BLACK.pieceRow) {
            color = Color.BLACK;
        } else {
            return null;
        }

        return color;
    }
}
