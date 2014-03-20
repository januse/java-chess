package rules;

import board.Board;
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
import piece.Piece;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static piece.Color.*;
import static piece.PieceType.*;

@RunWith(MockitoJUnitRunner.class)
public class StandardMovesAuthorizorTest {

    private Board board;
    private StandardMovesAuthorizor chessRules;

    @Mock
    private Player whitePlayer;
    @Mock
    private Player blackPlayer;

    @Before
    public void setUp() {
        when(whitePlayer.getColor()).thenReturn(Color.WHITE);
        when(blackPlayer.getColor()).thenReturn(Color.BLACK);

        board = new Board(whitePlayer, blackPlayer);
    }

    @Test
    public void pawnCanMoveTwoSquaresOnFirstMove() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(1, 0), new Position(3, 0), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void pawnCanTakeDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));
        board.putPieceAtPosition(new Piece(BLACK, QUEEN), new Position(2, 1));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(1, 0), new Position(2, 1), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void pawnCannotTakeForthrightly() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));
        board.putPieceAtPosition(new Piece(BLACK, QUEEN), new Position(1, 1));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(1, 0), new Position(1, 1), whitePlayer));

        assertFalse(chessRules.isMovePossible());
    }

    @Test
    public void pawnCanMoveForwardOne() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(1, 0), new Position(2, 0), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void pawnCannotMoveDiagonallyIfNotTaking() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(1, 0), new Position(2, 1), whitePlayer));

        assertFalse(chessRules.isMovePossible());
    }

    @Test
    public void pawnCannotMoveBackwards() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(1, 0), new Position(0, 0), whitePlayer));

        assertFalse(chessRules.isMovePossible());
    }

    @Test
    public void pawnCanEnPassant() {
        board.putPieceAtPosition(new Piece(BLACK, KING), new Position(7, 4));
        board.putPieceAtPosition(new Piece(WHITE, KING), new Position(0, 4));
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(4, 0));
        board.putPieceAtPosition(new Piece(BLACK, PAWN), new Position(6, 1));

        makeMove(new Position(6, 1), new Position(4, 1), blackPlayer);

        chessRules = new StandardMovesAuthorizor(createMove(new Position(4, 0), new Position(5, 1), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void pawnCannotEnPassantIfPieceItIsTryingToTakeWasNotLastMoved() {
        board.putPieceAtPosition(new Piece(BLACK, KING), new Position(7, 4));
        board.putPieceAtPosition(new Piece(WHITE, KING), new Position(0, 4));
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(4, 0));
        board.putPieceAtPosition(new Piece(BLACK, PAWN), new Position(6, 1));

        makeMove(new Position(6, 1), new Position(4, 1), blackPlayer);
        makeMove(new Position(0, 4), new Position(0, 3), whitePlayer);
        makeMove(new Position(7, 4), new Position(7, 3), blackPlayer);

        chessRules = new StandardMovesAuthorizor(createMove(new Position(4, 0), new Position(5, 1), whitePlayer));

        assertFalse(chessRules.isMovePossible());
    }

    @Test
    public void pawnCanPromote() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(6, 0));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(6, 0), new Position(7, 0), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void bishopCannotMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, BISHOP), new Position(0, 2));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 2), new Position(3, 2), whitePlayer));

        assertFalse(chessRules.isMovePossible());
    }

    @Test
    public void bishopCanMoveDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, BISHOP), new Position(0, 2));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 2), new Position(3, 5), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void rookCanMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, ROOK), new Position(0, 0));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 0), new Position(3, 0), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void rookCannotMoveDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, ROOK), new Position(0, 0));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 0), new Position(3, 3), whitePlayer));

        assertFalse(chessRules.isMovePossible());
    }

    @Test
    public void queenCanMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, QUEEN), new Position(0, 3));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 3), new Position(3, 3), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void queenCanMoveDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, QUEEN), new Position(0, 3));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 3), new Position(3, 6), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }
    
    @Test
    public void kingCanMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, KING), new Position(0, 3));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 3), new Position(0, 4), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void kingCanMoveDiagonally() {
        board.putPieceAtPosition(new Piece(WHITE, KING), new Position(0, 3));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 3), new Position(1, 3), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void kingCanQueenSideCastle() {
        board.putPieceAtPosition(new Piece(WHITE, KING), new Position(0, 4));
        board.putPieceAtPosition(new Piece(WHITE, ROOK), new Position(0, 0));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 4), new Position(0, 2), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void kingCanKingSideCastle() {
        board.putPieceAtPosition(new Piece(WHITE, KING), new Position(0, 4));
        board.putPieceAtPosition(new Piece(WHITE, ROOK), new Position(0, 7));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 4), new Position(0, 6), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void kingCannotCastleIfItHasAlreadyMoved() {
        board.putPieceAtPosition(new Piece(WHITE, KING), new Position(0, 4));
        board.putPieceAtPosition(new Piece(WHITE, ROOK), new Position(0, 0));

        makeMove(new Position(0, 4), new Position(0, 5), whitePlayer);
        makeMove(new Position(0, 5), new Position(0, 4), whitePlayer);

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 4), new Position(0, 2), whitePlayer));

        assertFalse(chessRules.isMovePossible());
    }

    @Test
    public void knightCanMoveInLShapes() {
        board.putPieceAtPosition(new Piece(WHITE, KNIGHT), new Position(0, 1));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 1), new Position(2, 0), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    @Test
    public void knightCannotMoveLinearly() {
        board.putPieceAtPosition(new Piece(WHITE, KNIGHT), new Position(0, 1));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 1), new Position(0, 3), whitePlayer));

        assertFalse(chessRules.isMovePossible());
    }

    @Test
    public void knightCanJumpOverOtherPieces() {
        board.putPieceAtPosition(new Piece(WHITE, KNIGHT), new Position(0, 1));
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 0));
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(1, 1));

        chessRules = new StandardMovesAuthorizor(createMove(new Position(0, 1), new Position(2, 0), whitePlayer));

        assertTrue(chessRules.isMovePossible());
    }

    private Move createMove(Position start, Position end, Player player) {
        MovePositions movePositions = new MovePositions(start, end);
        return new Move(movePositions, board, player);
    }

    private void makeMove(Position start, Position end, Player player) {
        MovePositions movePositions = new MovePositions(start, end);
        Move move = new Move(movePositions, board, player);
        board.makeMove(move);
    }
}
