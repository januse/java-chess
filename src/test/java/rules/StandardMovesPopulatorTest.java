package rules;

import board.Board;
import board.Position;
import game.Player;
import move.Move;
import move.MovePositions;
import move.PieceMove;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import piece.Color;
import piece.Piece;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static piece.Color.*;
import static piece.PieceType.*;

@RunWith(MockitoJUnitRunner.class)
public class StandardMovesPopulatorTest {

    Board board;
    StandardMovesPopulator movesPopulator;
    Piece whiteKing, blackQueen, whiteRook;
    Position start, end;
    @Mock
    private Player whitePlayer;
    @Mock
    private Player blackPlayer;

    @Before
    public void setUp() {
        when(whitePlayer.getColor()).thenReturn(Color.WHITE);
        when(blackPlayer.getColor()).thenReturn(Color.BLACK);

        board = new Board(whitePlayer, blackPlayer);
        whiteKing = new Piece(WHITE, KING);
        blackQueen = new Piece(BLACK, QUEEN);
        whiteRook = new Piece(WHITE, ROOK);
        start = new Position(0, 4);
        end = new Position(0, 5);
    }

    @Test
    public void populatorOnlyAddsMovingPieceForNormalMove() {
        board.putPieceAtPosition(whiteKing, start);

        movesPopulator = new StandardMovesPopulator(createMove(start, end));

        List<PieceMove> pieceMoves = movesPopulator.populateMove();

        assertTrue(pieceMoves.size() == 1);
        assertTrue(pieceMoves.contains(new PieceMove(start, end, whiteKing)));
    }

    @Test
    public void populatorAddsPiecesToTakeOffBoardForTakeMoves() {
        board.putPieceAtPosition(whiteKing, start);
        board.putPieceAtPosition(blackQueen, end);

        movesPopulator = new StandardMovesPopulator(createMove(start, end));

        List<PieceMove> pieceMoves = movesPopulator.populateMove();

        assertTrue(pieceMoves.size() == 2);
        assertTrue(pieceMoves.contains(new PieceMove(start, end, whiteKing)));
        assertTrue(pieceMoves.contains(new PieceMove(end, null, blackQueen)));
    }

    @Test
    public void populatorAddsBothKingAndRookForCastleMove() {
        board.putPieceAtPosition(whiteKing, start);
        board.putPieceAtPosition(whiteRook, new Position(0, 0));

        movesPopulator = new StandardMovesPopulator(createMove(start, new Position(0, 2)));

        List<PieceMove> pieceMoves = movesPopulator.populateMove();

        assertTrue(pieceMoves.size() == 2);
        assertTrue(pieceMoves.contains(new PieceMove(start, new Position(0, 2), whiteKing)));
        assertTrue(pieceMoves.contains(new PieceMove(new Position(0, 0), new Position(0, 3), whiteRook)));
    }

    @Test
    public void populatorAsksPlayerInPromotion() {
        board.putPieceAtPosition(new Piece(WHITE, PAWN), new Position(6, 0));

        movesPopulator = new StandardMovesPopulator(createMove(new Position(6, 0), new Position(7, 0)));

        movesPopulator.populateMove();

        verify(whitePlayer).getTypeForPromotion(board);
    }

    private Move createMove(Position start, Position end) {
        MovePositions movePositions = new MovePositions(start, end);
        return new Move(movePositions, board, whitePlayer);
    }
}
