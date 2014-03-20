package move;

import board.Board;
import board.Position;
import game.Player;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import piece.Color;
import piece.Piece;
import piece.PieceType;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoveTest {

    Board board;
    Position kingStartPosition = new Position(0, 4);
    Piece whiteKing = new Piece(Color.WHITE, PieceType.KING);
    @Mock
    Player whitePlayer;
    @Mock
    Player blackPlayer;

    @Before
    public void setUp() {
        when(whitePlayer.getColor()).thenReturn(Color.WHITE);
        when(blackPlayer.getColor()).thenReturn(Color.BLACK);

        board = new Board(whitePlayer, blackPlayer);
        board.putPieceAtPosition(whiteKing, kingStartPosition);
    }

    @Test
    public void makeMoveMovesPiece() {
        Move move = new Move(new MovePositions(kingStartPosition, new Position(1, 4)), board, whitePlayer);

        move.makeMove();

        assertThat(board.getPieceAtPosition(new Position(1, 4)), is(whiteKing));
        assertNull(board.getPieceAtPosition(kingStartPosition));
    }

    @Test
    public void undoMoveUndoesMove() {
        Move move = new Move(new MovePositions(kingStartPosition, new Position(1, 4)), board, whitePlayer);

        move.makeMove();
        move.undoMove();

        assertThat(board.getPieceAtPosition(kingStartPosition), is(whiteKing));
        assertFalse(whiteKing.hasMoved());
        assertNull(board.getPieceAtPosition(new Position(1, 4)));
    }

    @Test
    public void moveIntoCheckIsNotLegal() {
        board.putPieceAtPosition(new Piece(Color.BLACK, PieceType.ROOK), new Position(1,0));

        Move move = new Move(new MovePositions(kingStartPosition, new Position(1, 4)), board, whitePlayer);

        assertFalse(move.isLegal());
    }

    @Test
    public void moveIntoCheckIsPossible() {
        board.putPieceAtPosition(new Piece(Color.BLACK, PieceType.ROOK), new Position(1,0));

        Move move = new Move(new MovePositions(kingStartPosition, new Position(1, 4)), board, whitePlayer);

        assertTrue(move.isPossible());
    }

    @Test
    public void movePiecePinnedToKingOutOfTheWayIsNotLegal() {
        board.putPieceAtPosition(new Piece(Color.BLACK, PieceType.ROOK), new Position(0,0));
        board.putPieceAtPosition(new Piece(Color.WHITE, PieceType.BISHOP), new Position(3,0));

        Move move = new Move(new MovePositions(new Position(3,0), new Position(2, 1)), board, whitePlayer);

        assertFalse(move.isLegal());
    }

    @Test
    public void moveIsNotPossibleIfNoPieceAtStartPosition() {
        Move move = new Move(new MovePositions(new Position(3,0), new Position(2, 1)), board, whitePlayer);

        assertFalse(move.isPossible());
    }
}
