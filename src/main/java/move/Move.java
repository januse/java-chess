package move;

import board.Board;
import board.Position;
import piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private Position startPosition;
    private Position endPosition;
    private Board board;
    private Piece movingPiece;
    private IChessRules chessRules = new StandardChessRules();
    private List<PieceManipulation> piecesManipulatedInMove = new ArrayList<PieceManipulation>();

    public Move(Position startPosition, Position endPosition, Board board) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.board = board;
        this.movingPiece = board.getPieceAtPosition(startPosition);
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void makeMove() {
        populateManipulatedPieces();
        manipulatePieces();
    }

    public void makeInvertedMove() {
        invertManipulations();
        manipulatePieces();
        invertManipulations();
    }

    private void invertManipulations() {
        for (PieceManipulation manipulation : piecesManipulatedInMove) {
            Position temp = manipulation.startPosition;
            manipulation.startPosition = endPosition;
            manipulation.endPosition = temp;
        }
    }

    public boolean isLegal() {
        if (!isPossible()) {
            return false;
        }

        makeMove();
        boolean isLegal = !board.isInCheck(movingPiece.color);
        makeInvertedMove();

        return isLegal;
    }

    private void populateManipulatedPieces() {
        piecesManipulatedInMove.add(new PieceManipulation(startPosition, endPosition, movingPiece));
        if (board.getPieceAtPosition(endPosition) != null) {
            piecesManipulatedInMove.add(new PieceManipulation(endPosition, null, board.getPieceAtPosition(endPosition)));
        }
    }

    private void manipulatePieces() {
        // take off pieces first
        for (PieceManipulation manipulation : piecesManipulatedInMove) {
            if (manipulation.endPosition == null) {
                board.clearSquare(manipulation.startPosition);
            }
        }

        // if both pieces stay on board, order does not matter
        for (PieceManipulation manipulation : piecesManipulatedInMove) {
            if (manipulation.startPosition != null && manipulation.endPosition != null) {
                board.clearSquare(manipulation.startPosition);
                board.putPieceAtPosition(manipulation.piece, manipulation.endPosition);
            }
        }

        // put new pieces on last
        for (PieceManipulation manipulation : piecesManipulatedInMove) {
            if (manipulation.startPosition == null) {
                board.putPieceAtPosition(manipulation.piece, manipulation.endPosition);
            }
        }
    }

    public boolean isPossible() {
        if (startPosition.equals(endPosition)) {
            return false; // moving piece to its own position is never legal
        }

        if (movingPiece == null) {
            return false; // no piece at specified start position to move
        }

        switch (movingPiece.type) {
            case KNIGHT:
                return chessRules.isKnightMovePossible(startPosition, endPosition, board);
            case ROOK:
                return chessRules.isRookMovePossible(startPosition, endPosition, board);
            case BISHOP:
                return chessRules.isBishopMovePossible(startPosition, endPosition, board);
            case QUEEN:
                return chessRules.isQueenMovePossible(startPosition, endPosition, board);
            case KING:
                return chessRules.isKingMovePossible(startPosition, endPosition, board);
            case PAWN:
                return chessRules.isPawnMovePossible(startPosition, endPosition, board);
        }

        throw new RuntimeException("The piece is of an invalid type.  It's not really possible, but whatever");
    }
}