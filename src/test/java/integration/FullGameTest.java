package integration;

import board.Board;
import board.BoardSetter;
import board.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import piece.Color;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FullGameTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
        BoardSetter.setBoard(board);
    }

    @Test
    public void foolsMateEndsInCheckmate() {
        board.movePiece(new Position(1, 5), new Position(2, 5));
        board.movePiece(new Position(6, 4), new Position(4, 4));
        board.movePiece(new Position(1, 6), new Position(3, 6));
        board.movePiece(new Position(7, 3), new Position(3, 7));

        assertTrue(board.isCheckmated(Color.WHITE));
    }

    @Test
    public void isCheckmatedReturnsFalseIfThereIsAMoveToBlockCheck() {
        board.movePiece(new Position(1, 5), new Position(2, 5));
        board.movePiece(new Position(6, 4), new Position(4, 4));
        board.movePiece(new Position(7, 3), new Position(3, 7));

        assertFalse(board.isCheckmated(Color.WHITE));
    }
}
