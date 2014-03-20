package board;

import piece.Color;
import piece.Piece;
import piece.PieceType;

import static board.Position.*;
import static board.Position.QUEEN_COLUMN;
import static piece.Color.*;
import static piece.PieceType.*;
import static piece.PieceType.KING;
import static piece.PieceType.QUEEN;

public class BoardSetter {

    public static void setBoard(Board board) {
        placePawns(board);
        placePieces(board);
    }

    private static void placePawns(Board board) {
        for (int column = 0; column < Board.BOARD_SIZE; column++) {
            board.putPieceAtPosition(new Piece(BLACK, PAWN), new Position(BLACK.pawnRow, column));
            board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(WHITE.pawnRow, column));
        }
    }

    private static void placePieces(Board board) {
        for (Color color : Color.values()) {
            for (int column : new int[]{QUEEN_SIDE_ROOK_COLUMN, KING_SIDE_ROOK_COLUMN}) {
                board.putPieceAtPosition(new Piece(color, ROOK), new Position(color.pieceRow, column));
            }
            for (int column : new int[]{QUEEN_SIDE_BISHOP_COLUMN, KING_SIDE_BISHOP_COLUMN}) {
                board.putPieceAtPosition(new Piece(color, BISHOP), new Position(color.pieceRow, column));
            }
            for (int column : new int[]{QUEEN_SIDE_KNIGHT_COLUMN, KING_SIDE_KNIGHT_COLUMN}) {
                board.putPieceAtPosition(new Piece(color, KNIGHT), new Position(color.pieceRow, column));
            }
            board.putPieceAtPosition(new Piece(color, KING), new Position(color.pieceRow, KING_COLUMN));
            board.putPieceAtPosition(new Piece(color, QUEEN), new Position(color.pieceRow, QUEEN_COLUMN));
        }
    }
}


