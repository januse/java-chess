package board;

import move.Move;
import piece.Color;
import piece.Piece;
import piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public final int BOARD_SIZE = 8;
    private Piece[][] squares = new Piece[BOARD_SIZE][BOARD_SIZE];
    private List<Move> moves = new ArrayList<Move>();


    // still broken right now
    public boolean checkForCheckMate() {
        for (Position position: getAllPositionsOnBoard()) {

            if (getPieceAtPosition(position) != null && getPieceAtPosition(position).type == PieceType.KING) {

                if (getAllLegalMoves(position).isEmpty() && isUnderAttack(position)) {
                    return true;
                }

            }

        }
        return false;
    }

    public boolean checkForCheck() {
        for (Position position : getAllPositionsOnBoard()) {
            if (getPieceAtPosition(position) != null && getPieceAtPosition(position).type == PieceType.KING) {
                if (isUnderAttack(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isUnderAttack(Position position) {
        if (getPieceAtPosition(position) == null) {
            return false;
        }

        Color color = getPieceAtPosition(position).color;

        for (Position startPosition : getAllPositionsOnBoard()) {

            if (getPieceAtPosition(startPosition) != null && getPieceAtPosition(startPosition).color != color) {

                List<Move> moves = getAllLegalMoves(startPosition);

                for (Move move : moves) {

                    if (move.getEndPosition().equals(position)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<Move> getAllLegalMoves(Position startPosition) {

        List<Move> moves = new ArrayList<Move>();

        for (Position endPosition : getAllPositionsOnBoard()) {

            Move move = new Move(startPosition, endPosition, this);

            if (move.isLegal()) {

                moves.add(move);

            }
        }
        return moves;
    }

    public boolean movePiece(Position startPosition, Position endPosition) {
        Move move = new Move(startPosition, endPosition, this);
        if (move.isLegal()) {
            move.makeMove();
            moves.add(move);
            return true;
        }
        return false;
    }

    public void undoMove() {
        Move move = moves.get(moves.size() - 1);
        moves.remove(move);
        move.makeInvertedMove();
    }

    public Piece clearSquare(Position position) {
        Piece pieceOnSquare = squares[position.row][position.column];
        squares[position.row][position.column] = null;
        return pieceOnSquare;
    }

    public void putPieceAtPosition(Piece piece, Position position) {
        squares[position.row][position.column] = piece;
    }

    public Piece getPieceAtPosition(Position position) {
        return squares[position.row][position.column];
    }

    public List<Position> getAllPositionsOnBoard() {
        List<Position> positions = new ArrayList<Position>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                positions.add(new Position(i,j));
            }
        }
        return positions;
    }
}