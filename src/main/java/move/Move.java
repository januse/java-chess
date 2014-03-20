package move;

import board.Board;
import board.Position;
import game.Player;
import piece.Piece;
import rules.IChessRules;
import rules.StandardChessRules;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private final Player player;
    private final Board board;
    private final IChessRules chessRules;
    private MovePositions movePositions;
    private List<PieceMove> piecesMoved = new ArrayList<>();

    public Move(MovePositions movePositions, Board board, Player player) {
        this.movePositions = movePositions;
        this.board = board;
        this.player = player;
        chessRules = new StandardChessRules(this);
    }

    public Position getStartPosition() {
        return movePositions.start;
    }

    public Position getEndPosition() {
        return movePositions.end;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public void makeMove() {
        piecesMoved = chessRules.populatePiecesMoved();
        movePieces();
        for (PieceMove pieceMove : piecesMoved) {
            pieceMove.piece.moves.add(this);
        }
    }

    public void undoMove() {
        if (piecesMoved.isEmpty()) {
            throw new RuntimeException("You have to make a move before you can undo that move.");
        }
        invertPieceMoves();
        movePieces();
        invertPieceMoves();
        for (PieceMove pieceMove : piecesMoved) {
            pieceMove.piece.moves.remove(this);
        }
    }

    public boolean isLegal() {
        if (!isPossible()) {
            return false;
        }

        makeMove();
        boolean isLegal = !board.isInCheck(player.getColor());
        undoMove();

        return isLegal;
    }

    public boolean isPossible() {
        return board.getPieceAtPosition(movePositions.start) != null
                && !movePositions.areTheSame()
                && chessRules.isMovePossible();
    }

    public boolean contains(Piece piece) {
        for (PieceMove pieceMove : piecesMoved) {
            if (pieceMove.piece == piece) {
                return true;
            }
        }
        return false;
    }

    private void invertPieceMoves() {
        for (PieceMove pieceMove : piecesMoved) {
            Position temp = pieceMove.startPosition;
            pieceMove.startPosition = pieceMove.endPosition;
            pieceMove.endPosition = temp;
        }
    }

    private void movePieces() {
        // take off pieces first
        for (PieceMove manipulation : piecesMoved) {
            if (manipulation.endPosition == null) {
                board.clearSquare(manipulation.startPosition);
            }
        }

        // if all pieces stay on board, order does not matter
        for (PieceMove manipulation : piecesMoved) {
            if (manipulation.startPosition != null && manipulation.endPosition != null) {
                board.clearSquare(manipulation.startPosition);
                board.putPieceAtPosition(manipulation.piece, manipulation.endPosition);
            }
        }

        // put new pieces on last
        for (PieceMove manipulation : piecesMoved) {
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
        return this.movePositions.equals(otherMove.movePositions)
                && this.board == otherMove.board;
    }

    @Override
    public int hashCode() {
        return (193 * (269 + movePositions.hashCode())) + board.hashCode();
    }
}