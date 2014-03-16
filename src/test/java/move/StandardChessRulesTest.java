package move;

import board.Board;
import board.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import piece.Piece;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static piece.Color.*;
import static piece.PieceType.*;

@RunWith(MockitoJUnitRunner.class)
public class StandardChessRulesTest {

    Board board;
    StandardChessRules chessRules = new StandardChessRules();

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void pawnCanMoveTwoSquaresOnFirstMove() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));

        assertTrue(chessRules.isMovePossible(new Position(1, 0), new Position(3, 0), board));
    }

    @Test
    public void pawnCanTakeDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));
        board.putPieceAtPosition(new Piece(BLACK, QUEEN), new Position(2, 1));

        assertTrue(chessRules.isMovePossible(new Position(1, 0), new Position(2, 1), board));
    }

    @Test
    public void pawnCannotTakeForthrightly() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));
        board.putPieceAtPosition(new Piece(BLACK, QUEEN), new Position(1, 1));

        assertFalse(chessRules.isMovePossible(new Position(1, 0), new Position(1, 1), board));
    }

    @Test
    public void pawnCanMoveForwardOne() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));

        assertTrue(chessRules.isMovePossible(new Position(1, 0), new Position(2, 0), board));
    }

    @Test
    public void pawnCannotMoveDiagonallyIfNotTaking() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));

        assertFalse(chessRules.isMovePossible(new Position(1, 0), new Position(2, 1), board));
    }

    @Test
    public void pawnCannotMoveBackwards() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));

        assertFalse(chessRules.isMovePossible(new Position(1, 0), new Position(0, 0), board));
    }

    @Test
    public void bishopCannotMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, BISHOP), new Position(0, 2));

        assertFalse(chessRules.isMovePossible(new Position(0, 2), new Position(3, 2), board));
    }

    @Test
    public void bishopCanMoveDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, BISHOP), new Position(0, 2));

        assertTrue(chessRules.isMovePossible(new Position(0, 2), new Position(3, 5), board));
    }

    @Test
    public void rookCanMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, ROOK), new Position(0, 0));

        assertTrue(chessRules.isMovePossible(new Position(0, 0), new Position(3, 0), board));
    }

    @Test
    public void rookCannotMoveDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, ROOK), new Position(0, 0));

        assertFalse(chessRules.isMovePossible(new Position(0, 0), new Position(3, 3), board));
    }

    @Test
    public void queenCanMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, QUEEN), new Position(0, 3));

        assertTrue(chessRules.isMovePossible(new Position(0, 3), new Position(3, 3), board));
    }

    @Test
    public void queenCanMoveDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, QUEEN), new Position(0, 3));

        assertTrue(chessRules.isMovePossible(new Position(0, 3), new Position(3, 6), board));
    }
    
    @Test
    public void kingCanMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, KING), new Position(0, 3));

        assertTrue(chessRules.isMovePossible(new Position(0, 3), new Position(0, 4), board));
    }

    @Test
    public void kingCanMoveDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, KING), new Position(0, 3));

        assertTrue(chessRules.isMovePossible(new Position(0, 3), new Position(1, 3), board));
    }

    @Test
    public void knightCanMoveInLShapes() {
        board.putPieceAtPosition(new Piece(WHITE, KNIGHT), new Position(0, 1));

        assertTrue(chessRules.isMovePossible(new Position(0, 1), new Position(2, 0), board));
    }

    @Test
    public void knightCannotMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, KNIGHT), new Position(0, 1));

        assertFalse(chessRules.isMovePossible(new Position(0, 1), new Position(0, 3), board));
    }

    @Test
    public void knightCanJumpOverOtherPieces() {
        board.putPieceAtPosition(new Piece(WHITE, KNIGHT), new Position(0, 1));
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 1));

        assertTrue(chessRules.isMovePossible(new Position(0, 1), new Position(2, 0), board));
    }
}
