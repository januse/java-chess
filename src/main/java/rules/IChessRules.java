package rules;

import board.Board;
import board.Position;
import move.PieceManipulation;

import java.util.List;

public interface IChessRules {

    public boolean isMovePossible(Position startPosition, Position endPosition, Board board);
    public List<PieceManipulation> populatePieceManipulations(Position startPosition, Position endPosition, Board board);
}
