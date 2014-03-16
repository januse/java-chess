package board;

import move.Move;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import piece.Color;
import piece.Piece;
import piece.PieceType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BoardTest {

    Board board;
    Position kingStartPosition = new Position(0, 4);
    Piece whiteKing = new Piece(Color.WHITE, PieceType.KING);

    @Before
    public void setUp() {
        board = new Board();
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

        board.movePiece(kingStartPosition, moveToPosition);

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

        assertThat(legalMoves, is((List) new ArrayList<Move>(){{
            add(new Move(kingStartPosition, new Position(0, 3), board));
            add(new Move(kingStartPosition, new Position(0, 5), board));}}
        ));
    }
}
