package board;

import game.Player;
import move.Move;
import move.MovePositions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import piece.Color;
import piece.Piece;
import piece.PieceType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardTest {

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
    public void clearSquareClearsSquare() {
        board.clearSquare(kingStartPosition);

        assertNull(board.getPieceAtPosition(kingStartPosition));
    }

    @Test
    public void movePieceMovesPiece() {
        Position moveToPosition = new Position(1, 5);

        board.makeMove(createMove(kingStartPosition, moveToPosition));

        assertThat(board.getPieceAtPosition(moveToPosition), is(whiteKing));
        assertNull(board.getPieceAtPosition(kingStartPosition));
    }

    @Test
    public void isInCheckReturnsTrueWhenKingIsInCheck() {
        board.putPieceAtPosition(new Piece(Color.BLACK, PieceType.ROOK), new Position(0,0));

        assertTrue(board.isInCheck(Color.WHITE));
    }

    @Test
    public void isCheckmatedReturnsTrueWhenKingIsCheckmated() {
        board.putPieceAtPosition(new Piece(Color.BLACK, PieceType.ROOK), new Position(0,0));
        board.putPieceAtPosition(new Piece(Color.BLACK, PieceType.ROOK), new Position(1,0));

        assertTrue(board.isCheckmated(Color.WHITE));
    }

    @Test
    public void isStalematedReturnsTrueWhenKingIsStalemated() {
        board.putPieceAtPosition(new Piece(Color.BLACK, PieceType.QUEEN), new Position(2,3));
        board.putPieceAtPosition(new Piece(Color.BLACK, PieceType.ROOK), new Position(1,0));

        assertTrue(board.isStalemated(Color.WHITE));
    }

    @Test
    public void isInCheckReturnsFalseWhenKingNotInCheck() {
        assertFalse(board.isInCheck(Color.WHITE));
    }

    @Test
    public void isCheckmatedReturnsFalseWhenKingIsNotCheckmated() {
        assertFalse(board.isCheckmated(Color.WHITE));
    }

    @Test
    public void isStalematedReturnsFalseWhenKingIsNotStalemated() {
        assertFalse(board.isStalemated(Color.WHITE));
    }

    @Test
    public void getAllLegalMovesByColorReturnsAllLegalMoves() {
        board.putPieceAtPosition(new Piece(Color.BLACK, PieceType.ROOK), new Position(1,0));

        List<Move> legalMoves = board.getAllLegalMovesByColor(Color.WHITE);

        List<Move> expectedLegalMoves = new ArrayList<Move>(){{
                                                add(createMove(kingStartPosition, new Position(0, 3)));
                                                add(createMove(kingStartPosition, new Position(0, 5)));}};

        assertThat(legalMoves, is(expectedLegalMoves));
    }

    private Move createMove(Position start, Position end) {
        MovePositions movePositions = new MovePositions(start, end);
        return new Move(movePositions, board, whitePlayer);
    }
}
