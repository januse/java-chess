package move;

import board.Board;
import board.Position;

public interface IChessRules {

    public boolean isPawnMovePossible(Position startPosition, Position endPosition, Board board);

    public boolean isRookMovePossible(Position startPosition, Position endPosition, Board board);

    public boolean isKnightMovePossible(Position startPosition, Position endPosition, Board board);

    public boolean isBishopMovePossible(Position startPosition, Position endPosition, Board board);

    public boolean isQueenMovePossible(Position startPosition, Position endPosition, Board board);

    public boolean isKingMovePossible(Position startPosition, Position endPosition, Board board);
}
