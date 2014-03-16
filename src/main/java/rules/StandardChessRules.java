package rules;

import board.Board;
import board.Position;
import move.PieceManipulation;

import java.io.IOException;
import java.util.List;

public class StandardChessRules implements IChessRules {
    private StandardMovesAuthorizor movesAuthorizor = new StandardMovesAuthorizor();
    private StandardMovesPopulator movesPopulator = new StandardMovesPopulator();

    @Override
    public boolean isMovePossible(Position startPosition, Position endPosition, Board board) {
        return movesAuthorizor.isMovePossible(startPosition, endPosition, board);
    }

    @Override
    public List<PieceManipulation> populatePieceManipulations(Position startPosition, Position endPosition, Board board)  {
        return movesPopulator.populateMove(startPosition, endPosition, board);
    }
}
