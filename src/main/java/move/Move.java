package move;

import board.Board;
import board.Position;
import piece.Piece;
import rules.IChessRules;
import rules.StandardChessRules;

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

    public Position getEndPosition() {
        return endPosition;
    }

    public void makeMove() {
        piecesManipulatedInMove = chessRules.populatePieceManipulations(startPosition, endPosition, board);
        manipulatePieces();
        for (PieceManipulation pieceManipulation : piecesManipulatedInMove) {
            pieceManipulation.piece.moves.add(this);
        }
    }

    public void undoMove() {
        if (piecesManipulatedInMove.isEmpty()) {
            throw new RuntimeException("You have to make a move before you can undo that move.");
        }
        invertManipulations();
        manipulatePieces();
        invertManipulations();
        for (PieceManipulation pieceManipulation : piecesManipulatedInMove) {
            pieceManipulation.piece.moves.remove(this);
        }
    }

    public boolean isLegal() {
        if (!isPossible()) {
            return false;
        }

        makeMove();
        boolean isLegal = !board.isInCheck(movingPiece.color);
        undoMove();

        return isLegal;
    }

    public boolean isPossible() {
        return movingPiece != null
                && !startPosition.equals(endPosition)
                && chessRules.isMovePossible(startPosition, endPosition, board);
    }

    public boolean contains(Piece piece) {
        for (PieceManipulation pieceManipulation : piecesManipulatedInMove) {
            if (pieceManipulation.piece == piece) {
                return true;
            }
        }
        return false;
    }

    private void invertManipulations() {
        for (PieceManipulation manipulation : piecesManipulatedInMove) {
            Position temp = manipulation.startPosition;
            manipulation.startPosition = manipulation.endPosition;
            manipulation.endPosition = temp;
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

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Move)) {
            return false;
        }
        Move otherMove = (Move) object;
        return this.startPosition.equals(otherMove.startPosition)
                && this.endPosition.equals(otherMove.endPosition)
                && this.board == otherMove.board;
    }

    @Override
    public int hashCode() {
        return (193 * ( 17 * (269 + startPosition.hashCode()) + endPosition.hashCode()) + board.hashCode());
    }
}