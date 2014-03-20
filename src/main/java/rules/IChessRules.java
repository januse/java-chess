package rules;

import move.PieceMove;

import java.util.List;

public interface IChessRules {
    public boolean isMovePossible();
    public List<PieceMove> populatePiecesMoved();
}
