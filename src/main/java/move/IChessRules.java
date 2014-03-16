package move;

import board.Board;
import board.Position;

public interface IChessRules {

    public boolean isMovePossible(Position startPosition, Position endPosition, Board board);
}
