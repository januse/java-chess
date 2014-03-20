package rules;

import board.Board;
import board.Position;
import move.Move;
import move.PieceMove;

import java.util.List;

public class StandardChessRules implements IChessRules {
    private final StandardMovesAuthorizor movesAuthorizor;
    private final StandardMovesPopulator movesPopulator;

    public StandardChessRules(Move move) {
        this.movesAuthorizor = new StandardMovesAuthorizor(move);
        this.movesPopulator = new StandardMovesPopulator(move);
    }

    @Override
    public boolean isMovePossible() {
        return movesAuthorizor.isMovePossible();
    }

    @Override
    public List<PieceMove> populatePiecesMoved()  {
        return movesPopulator.populateMove();
    }
}
