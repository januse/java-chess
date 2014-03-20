package game;

import board.Board;
import move.Move;
import piece.Color;
import piece.PieceType;

public abstract class Player {
    protected Color color;
    public Player(Color color) {
        this.color = color;
    }
    public Color getColor() {return color;}
    public abstract Move makeNextMove(Board board);
    public abstract PieceType getTypeForPromotion(Board board);
}
