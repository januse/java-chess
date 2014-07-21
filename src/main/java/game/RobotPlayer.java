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
        return askKasparov(board);
    }

    @Override
    public PieceType getTypeForPromotion(Board board) {
        return PieceType.QUEEN;
    }

    private Move askKasparov(Board board) {
        List<Move> moves = board.getAllLegalMovesByColor(color);
        Move moveToMake = moves.get(kasparov.nextInt(moves.size()));
        for (Move move : moves) {
            if (move.isCheckMate()) {
                moveToMake = move;
            }

            if (move.isCheck() && !moveToMake.isCheckMate()) {
                moveToMake = move;
            }

            if (move.isATake() && !moveToMake.isCheckMate() && !moveToMake.isCheck()) {
                moveToMake = move;
            }
        }
        return moveToMake;
    }
}
