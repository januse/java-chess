package integration;

import board.Board;
import board.BoardSetter;
import board.Position;
import game.Player;
import move.Move;
import move.MovePositions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import piece.Color;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FullGameTest {

    @Mock
    private Player whitePlayer;
    @Mock
    private Player blackPlayer;

    private Board board;

    @Before
    public void setUp() {
        when(whitePlayer.getColor()).thenReturn(Color.WHITE);
        when(blackPlayer.getColor()).thenReturn(Color.BLACK);

        board = new Board(whitePlayer, blackPlayer);
        BoardSetter.setBoard(board);
    }

    @Test
    public void foolsMateEndsInCheckmate() {
        board.makeMove(createMove(new Position(1, 5), new Position(2, 5), whitePlayer));
        board.makeMove(createMove(new Position(6, 4), new Position(4, 4), blackPlayer));
        board.makeMove(createMove(new Position(1, 6), new Position(3, 6), whitePlayer));
        board.makeMove(createMove(new Position(7, 3), new Position(3, 7), blackPlayer));

        assertTrue(board.isCheckmated(Color.WHITE));
    }

    @Test
    public void isCheckmatedReturnsFalseIfThereIsAMoveToBlockCheck() {
        board.makeMove(createMove(new Position(1, 5), new Position(2, 5), whitePlayer));
        board.makeMove(createMove(new Position(6, 4), new Position(4, 4), blackPlayer));
        board.makeMove(createMove(new Position(7, 3), new Position(3, 7), blackPlayer));

        assertFalse(board.isCheckmated(Color.WHITE));
    }

    private Move createMove(Position start, Position end, Player player) {
        MovePositions movePositions = new MovePositions(start, end);
        return new Move(movePositions, board, player);
    }
}
