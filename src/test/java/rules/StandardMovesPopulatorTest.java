package rules;

import board.Board;
import board.Position;
import move.PieceManipulation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import piece.Piece;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static piece.Color.*;
import static piece.PieceType.*;

@RunWith(MockitoJUnitRunner.class)
public class StandardMovesPopulatorTest {

    Board board;
    StandardMovesPopulator chessRules = new StandardMovesPopulator();
    Piece whiteKing, blackQueen, whiteRook;
    Position start, end;

    @Before
    public void setUp() {
        whiteKing = new Piece(WHITE, KING);
        blackQueen = new Piece(BLACK, QUEEN);
        whiteRook = new Piece(WHITE, ROOK);
        start = new Position(0, 4);
        end = new Position(0, 5);
        board = new Board();
    }

    @Test
    public void populatorOnlyAddsMovingPieceForNormalMove() {
        board.putPieceAtPosition(whiteKing, start);

        List<PieceManipulation> pieceManipulations = chessRules.populateMove(start, end, board);

        assertTrue(pieceManipulations.size() == 1);
        assertTrue(pieceManipulations.contains(new PieceManipulation(start, end, whiteKing)));
    }

    @Test
    public void populatorAddsPiecesToTakeOffBoardForTakeMoves() {
        board.putPieceAtPosition(whiteKing, start);
        board.putPieceAtPosition(blackQueen, end);

        List<PieceManipulation> pieceManipulations = chessRules.populateMove(start, end, board);

        assertTrue(pieceManipulations.size() == 2);
        assertTrue(pieceManipulations.contains(new PieceManipulation(start, end, whiteKing)));
        assertTrue(pieceManipulations.contains(new PieceManipulation(end, null, blackQueen)));
    }

    @Test
    public void populatorAddsBothKingAndRookForCastleMove() {
        board.putPieceAtPosition(whiteKing, start);
        board.putPieceAtPosition(whiteRook, new Position(0, 0));

        List<PieceManipulation> pieceManipulations = chessRules.populateMove(start, new Position(0, 2), board);

        assertTrue(pieceManipulations.size() == 2);
        assertTrue(pieceManipulations.contains(new PieceManipulation(start, new Position(0, 2), whiteKing)));
        assertTrue(pieceManipulations.contains(new PieceManipulation(new Position(0, 0), new Position(0, 3), whiteRook)));
    }
}
