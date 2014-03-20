package game;

import board.Board;
import move.Move;
import piece.Color;
import piece.PieceType;

import java.util.List;
import java.util.Random;

// TODO: improve robot player
public class RobotPlayer extends Player {

    private Random kasparov = new Random();

    public RobotPlayer(Color color) {
        super(color);
    }

    @Override
    public Move makeNextMove(Board board) {
        List<Move> legalMoves = board.getAllLegalMovesByColor(color);
        return askKasparov(legalMoves);
    }

    @Override
    public PieceType getTypeForPromotion(Board board) {
        return PieceType.QUEEN;
    }

    private Move askKasparov(List<Move> moves) {
        return moves.get(kasparov.nextInt(moves.size()));
    }
}
