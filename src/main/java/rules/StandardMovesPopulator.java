package rules;

import board.Board;
import board.Position;
import move.PieceManipulation;
import piece.Color;
import piece.Piece;
import piece.PieceType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static board.Position.*;
import static java.lang.Math.abs;
import static piece.PieceType.*;

/**
 * Moves should be validated before they are populated
 */
public class StandardMovesPopulator {
    public List<PieceManipulation> populateMove(Position startPosition, Position endPosition, Board board) {
        if (moveIsCastle(startPosition, endPosition, board)) {
            return populateCastleMove(startPosition, endPosition, board);
        }

        if (moveIsPromotion(startPosition, endPosition, board)) {
            return populatePromotionMove(startPosition, endPosition, board);
        }

        if (moveIsEnPassant(startPosition, endPosition, board)) {
            return populateEnPassantMave(startPosition, endPosition, board);
        }

        return populateNormalMove(startPosition, endPosition, board);
    }

    private boolean moveIsCastle(Position startPosition, Position endPosition, Board board) {
        return board.getPieceAtPosition(startPosition).type == KING
                && abs(startPosition.column - endPosition.column) > 1;
    }

    private boolean moveIsPromotion(Position startPosition, Position endPosition, Board board) {
        return board.getPieceAtPosition(startPosition).type == PAWN
                && endPosition.row == board.getPieceAtPosition(startPosition).color.opposite().pieceRow;
    }

    private boolean moveIsEnPassant(Position startPosition, Position endPosition, Board board) {
        return board.getPieceAtPosition(startPosition).type == PAWN
                && startPosition.column != endPosition.column
                && board.getPieceAtPosition(endPosition) == null;
    }

    private List<PieceManipulation> populatePromotionMove(Position startPosition, Position endPosition, Board board) {
        List<PieceManipulation> piecesManipulatedInMove = new ArrayList<PieceManipulation>();

        Piece pawn = board.getPieceAtPosition(startPosition);

        piecesManipulatedInMove.add(new PieceManipulation(startPosition, null, pawn));

        if (board.getPieceAtPosition(endPosition) != null) {
            piecesManipulatedInMove.add(new PieceManipulation(endPosition, null, board.getPieceAtPosition(endPosition)));
        }

        PieceType newPieceType;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("What piece type would you like to promote to?");
            newPieceType = PieceType.valueOf(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException("That's not a type you can promote to");
        }

        piecesManipulatedInMove.add(new PieceManipulation(null, endPosition, new Piece(pawn.color, newPieceType)));

        return piecesManipulatedInMove;
    }

    private List<PieceManipulation> populateCastleMove(Position startPosition, Position endPosition, Board board) {
        List<PieceManipulation> piecesManipulatedInMove = new ArrayList<PieceManipulation>();

        Piece king = board.getPieceAtPosition(startPosition);

        piecesManipulatedInMove.add(new PieceManipulation(startPosition, endPosition, king));

        int rookStartColumn, rookEndColumn;
        if (endPosition.column == KING_SIDE_BISHOP_COLUMN) {
            rookStartColumn = KING_SIDE_ROOK_COLUMN;
            rookEndColumn = KING_SIDE_BISHOP_COLUMN;
        } else {
            rookStartColumn = QUEEN_SIDE_ROOK_COLUMN;
            rookEndColumn = QUEEN_COLUMN;
        }

        piecesManipulatedInMove.add(new PieceManipulation(
                new Position(king.color.pieceRow, rookStartColumn),
                new Position(king.color.pieceRow, rookEndColumn),
                board.getPieceAtPosition(new Position(king.color.pieceRow, rookStartColumn))));

        return piecesManipulatedInMove;
    }

    private List<PieceManipulation> populateEnPassantMave(Position startPosition, Position endPosition, Board board) {
        List<PieceManipulation> piecesManipulatedInMove = new ArrayList<PieceManipulation>();

        Piece pawn = board.getPieceAtPosition(startPosition);

        piecesManipulatedInMove.add(new PieceManipulation(startPosition, endPosition, pawn));

        int difference = pawn.color == Color.WHITE? -1 : 1;
        Position pawnBeingTakenPosition = new Position(endPosition.row + difference, endPosition.column);

        piecesManipulatedInMove.add(
                new PieceManipulation(
                        pawnBeingTakenPosition,
                        null,
                        board.getPieceAtPosition(pawnBeingTakenPosition)));

        return piecesManipulatedInMove;
    }

    private List<PieceManipulation> populateNormalMove(Position startPosition, Position endPosition, Board board) {

        List<PieceManipulation> piecesManipulatedInMove = new ArrayList<PieceManipulation>();

        piecesManipulatedInMove.add(new PieceManipulation(startPosition, endPosition, board.getPieceAtPosition(startPosition)));

        if (board.getPieceAtPosition(endPosition) != null) {
            piecesManipulatedInMove.add(new PieceManipulation(endPosition, null, board.getPieceAtPosition(endPosition)));
        }

        return piecesManipulatedInMove;
    }
}