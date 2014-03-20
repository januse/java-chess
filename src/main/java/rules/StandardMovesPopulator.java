package rules;

import board.Board;
import board.Position;
import game.Player;
import move.Move;
import move.PieceMove;
import piece.Color;
import piece.Piece;
import piece.PieceType;

import java.util.ArrayList;
import java.util.List;

import static board.Position.*;
import static java.lang.Math.abs;
import static piece.PieceType.*;

/**
 * Moves should be validated before they are populated
 */
public class StandardMovesPopulator {

    private final Position start;
    private final Position end;
    private final Board board;
    private final Player player;
    private List<PieceMove> piecesMoved;

    public StandardMovesPopulator(Move move) {
        this.start = move.getStartPosition();
        this.end = move.getEndPosition();
        this.board = move.getBoard();
        this.player = move.getPlayer();
    }

    public List<PieceMove> populateMove() {
        piecesMoved = new ArrayList<>();

        if (moveIsCastle()) {
            populateCastleMove();
        }

        else if (moveIsPromotion()) {
            populatePromotionMove();
        }

        else if (moveIsEnPassant()) {
            populateEnPassantMave();
        }

        else {
            populateNormalMove();
        }

        return piecesMoved;
    }

    private boolean moveIsCastle() {
        return board.getPieceAtPosition(start).type == KING
                && abs(start.column - end.column) > 1;
    }

    private boolean moveIsPromotion() {
        return board.getPieceAtPosition(start).type == PAWN
                && end.row == board.getPieceAtPosition(start).color.opposite().pieceRow;
    }

    private boolean moveIsEnPassant() {
        return board.getPieceAtPosition(start).type == PAWN
                && start.column != end.column
                && board.getPieceAtPosition(end) == null;
    }

    private void populatePromotionMove() {
        piecesMoved.add(new PieceMove(start, null, board.getPieceAtPosition(start)));
        addTakenPiece();
        addPromotedPiece();
    }

    private void populateCastleMove() {
        addPieceAtStartPosition();
        addRookToCastle();
    }

    private void populateEnPassantMave() {
        addPieceAtStartPosition();
        addPawnTakenInEnPassant();
    }

    private void populateNormalMove() {
        addPieceAtStartPosition();
        addTakenPiece();
    }

    private void addPieceAtStartPosition() {
        piecesMoved.add(new PieceMove(start, end, board.getPieceAtPosition(start)));
    }

    private void addTakenPiece() {
        if (board.getPieceAtPosition(end) != null) {
            piecesMoved.add(new PieceMove(end, null, board.getPieceAtPosition(end)));
        }
    }

    private void addPromotedPiece() {
        PieceType newPieceType;
        newPieceType = player.getTypeForPromotion(board);
        piecesMoved.add(new PieceMove(null, end, new Piece(player.getColor(), newPieceType)));
    }

    private void addRookToCastle() {
        int rookStartColumn, rookEndColumn;
        if (end.column == KING_SIDE_KNIGHT_COLUMN) {
            rookStartColumn = KING_SIDE_ROOK_COLUMN;
            rookEndColumn = KING_SIDE_BISHOP_COLUMN;
        } else {
            rookStartColumn = QUEEN_SIDE_ROOK_COLUMN;
            rookEndColumn = QUEEN_COLUMN;
        }

        piecesMoved.add(new PieceMove(
                new Position(player.getColor().pieceRow, rookStartColumn),
                new Position(player.getColor().pieceRow, rookEndColumn),
                board.getPieceAtPosition(new Position(player.getColor().pieceRow, rookStartColumn))));
    }

    private void addPawnTakenInEnPassant() {
        int difference = player.getColor() == Color.WHITE? -1 : 1;
        Position pawnBeingTakenPosition = new Position(end.row + difference, end.column);

        piecesMoved.add(
                new PieceMove(
                        pawnBeingTakenPosition,
                        null,
                        board.getPieceAtPosition(pawnBeingTakenPosition)));
    }
}