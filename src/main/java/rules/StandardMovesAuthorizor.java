package rules;

import board.Board;
import board.Position;
import move.Move;
import piece.Color;
import piece.Piece;

import static board.Position.*;
import static java.lang.Math.*;
import static piece.PieceType.*;

public class StandardMovesAuthorizor {

    public boolean isMovePossible(Position startPosition, Position endPosition, Board board) {
        switch (board.getPieceAtPosition(startPosition).type) {
            case KNIGHT:
                return isKnightMovePossible(startPosition, endPosition, board);
            case ROOK:
                return isRookMovePossible(startPosition, endPosition, board);
            case BISHOP:
                return isBishopMovePossible(startPosition, endPosition, board);
            case QUEEN:
                return isQueenMovePossible(startPosition, endPosition, board);
            case KING:
                return isKingMovePossible(startPosition, endPosition, board);
            case PAWN:
                return isPawnMovePossible(startPosition, endPosition, board);
        }
        throw new RuntimeException("The type of piece you are trying to move shouldn't exist on the board");
    }

    private boolean isRookMovePossible(Position start, Position end, Board board) {
        return !endOccupiedByPieceOfSameColor(start, end, board)
                && moveIsLinear(start, end)
                && !somethingIsInTheWay(start, end, board);
    }

    private boolean isBishopMovePossible(Position start, Position end, Board board) {
        return !endOccupiedByPieceOfSameColor(start, end, board)
                && moveIsDiagonal(start, end)
                && !somethingIsInTheWay(start, end, board);
    }

    private boolean isQueenMovePossible(Position start, Position end, Board board) {
        return !endOccupiedByPieceOfSameColor(start, end, board)
                && (moveIsDiagonal(start, end) || moveIsLinear(start, end))
                && !somethingIsInTheWay(start, end, board);
    }

    private boolean isKnightMovePossible(Position start, Position end, Board board) {
        return !endOccupiedByPieceOfSameColor(start, end, board)
                && moveIsLShaped(start, end);

    }

    private boolean isKingMovePossible(Position start, Position end, Board board) {
        if (endOccupiedByPieceOfSameColor(start, end, board)) {
            return false;
        }

        // can castle
        Piece king = board.getPieceAtPosition(start);
        if (end.column == KING_SIDE_KNIGHT_COLUMN && !king.hasMoved()
                && board.getPieceAtPosition(new Position(king.color.pieceRow, KING_SIDE_ROOK_COLUMN)) != null
                && !board.getPieceAtPosition(new Position(king.color.pieceRow, KING_SIDE_ROOK_COLUMN)).hasMoved()
                && new Move(start, new Position(end.row, KING_SIDE_BISHOP_COLUMN), board).isLegal()) {
            return true;
        } else if (end.column == QUEEN_SIDE_BISHOP_COLUMN && !king.hasMoved()
                && board.getPieceAtPosition(new Position(king.color.pieceRow, QUEEN_SIDE_ROOK_COLUMN)) != null
                && !board.getPieceAtPosition(new Position(king.color.pieceRow, QUEEN_SIDE_ROOK_COLUMN)).hasMoved()
                && board.getPieceAtPosition(new Position(king.color.pieceRow, QUEEN_SIDE_KNIGHT_COLUMN)) == null
                && new Move(start, new Position(end.row, QUEEN_COLUMN), board).isLegal()) {
            return true;
        }

        // can make normal move
        return abs(rowDistance(start, end)) <= 1 && abs(columnDistance(start, end)) <= 1;
    }

    private boolean isPawnMovePossible(Position start, Position end, Board board) {
        // must be linear or diagonal move
        if (!moveIsDiagonal(start, end) && !moveIsLinear(start, end)) {
            return false;
        }

        // can only move forward
        if (!moveIsForward(start, end, board)) {
            return false;
        }

        // cannot move onto another piece of same color
        if (endOccupiedByPieceOfSameColor(start, end ,board)) {
            return false;
        }

        // can move two squares as first move
        if (pawnIsOnStartRow(start, board) && moveIsLinear(start, end) && board.getPieceAtPosition(end) == null
                && abs(start.row - end.row) == 2 && !somethingIsInTheWay(start, start, board)) {
            return true;
        }

        // can move one step forward
        if (abs(rowDistance(start, end)) == 1 && columnDistance(start, end) == 0) {
            return true;
        }

        if (abs(rowDistance(start, end)) == 1 && abs(columnDistance(start, end)) == 1) {
            // can make diagonal move of one to take
            if (endOccupiedByPieceOfDifferentColor(start, end, board)) {
                return true;
            }

            // can en passant
            Piece pawn = board.getPieceAtPosition(start);
            if (end.row == pawn.color.opposite().pawnRow + pawn.color.opposite().directionOfTravel()
                    && board.getPieceAtPosition(new Position(end.row - pawn.color.directionOfTravel(), end.column)) != null) {

                Piece toBeTaken = board.getPieceAtPosition(new Position(end.row - pawn.color.directionOfTravel(), end.column));
                if (toBeTaken.type == PAWN &&  !board.moves.isEmpty()
                        && board.moves.get(board.moves.size() - 1).contains(toBeTaken)) {
                    return true;
                }
            }
        }

        return false;
    }

    private int rowDistance(Position start, Position end) {
        return start.row - end.row;
    }

    private int columnDistance(Position start, Position end) {
        return start.column - end.column;
    }

    private boolean moveIsDiagonal(Position start, Position end) {
        return abs(rowDistance(start, end)) == abs(columnDistance(start, end));
    }

    private boolean moveIsLinear(Position start, Position end) {
        return rowDistance(start, end) == 0 || columnDistance(start, end) == 0;
    }

    private boolean moveIsLShaped(Position start, Position end) {
        return (abs(rowDistance(start, end)) == 1 && abs(columnDistance(start, end)) == 2)
                || (abs(rowDistance(start, end)) == 2 && abs(columnDistance(start, end)) == 1);
    }

    private boolean pawnIsOnStartRow(Position position, Board board) {
        return position.row == board.getPieceAtPosition(position).color.pawnRow;
    }

    private boolean endOccupiedByPieceOfSameColor(Position start, Position end, Board board) {
        Color color = board.getPieceAtPosition(start).color;
        return board.getPieceAtPosition(end) != null && board.getPieceAtPosition(end).color == color;
    }

    private boolean endOccupiedByPieceOfDifferentColor(Position start, Position end, Board board) {
        Color color = board.getPieceAtPosition(start).color;
        return board.getPieceAtPosition(end) != null && board.getPieceAtPosition(end).color != color;
    }

    private boolean moveIsForward(Position start, Position end, Board board) {
        if (start.row - end.row < 0 && board.getPieceAtPosition(start).color == Color.WHITE) {
            return true;
        } else if (start.row - end.row > 0 && board.getPieceAtPosition(start).color == Color.BLACK) {
            return true;
        }
        return false;
    }

    private boolean somethingIsInTheWay(Position start, Position end, Board board) {
        Position position = new Position(start.row, start.column);

        for (int i = 1; i < max(abs(rowDistance(start, end)), abs(columnDistance(start, end))); i++) {

            if (columnDistance(start, end) != 0) {
                if (columnDistance(start, end) < 0) {
                    position.column = start.column + i;
                } else {
                    position.column = start.column - i;
                }
            }

            if (rowDistance(start, end) != 0) {
                if (rowDistance(start, end) < 0) {
                    position.row = start.row + i;
                } else {
                    position.row = start.row - i;
                }
            }

            if (board.getPieceAtPosition(position) != null) {
                return true;
            }
        }

        return false;
    }
}