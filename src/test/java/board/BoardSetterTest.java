package board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import piece.Color;
import piece.Piece;

import static board.Position.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static piece.Color.*;
import static piece.PieceType.*;


@RunWith(MockitoJUnitRunner.class)
public class BoardSetterTest {

    @Mock
    Board mockBoard;

    BoardSetter boardSetter = new BoardSetter();

    @Test
    public void boardSetterPlacesPiecesCorrectly() {
        BoardSetter.setBoard(mockBoard);

        verifyPawnsArePlacedCorrectly();
        verifyPiecesArePlacedCorrectly();
        verifyNoMoreInteractions(mockBoard);
    }

    private void verifyPawnsArePlacedCorrectly() {
        for (int column = 0; column < Board.BOARD_SIZE; column++) {
            verify(mockBoard).putPieceAtPosition(new Piece(BLACK, PAWN), new Position(BLACK.pawnRow, column));
            verify(mockBoard).putPieceAtPosition(new Piece(WHITE, PAWN), new Position(WHITE.pawnRow, column));
        }
    }

    private void verifyPiecesArePlacedCorrectly() {
        for (Color color : Color.values()) {
            for (int column : new int[]{QUEEN_SIDE_ROOK_COLUMN, KING_SIDE_ROOK_COLUMN}) {
                verify(mockBoard).putPieceAtPosition(new Piece(color, ROOK), new Position(color.pieceRow, column));
            }
            for (int column : new int[]{QUEEN_SIDE_BISHOP_COLUMN, KING_SIDE_BISHOP_COLUMN}) {
                verify(mockBoard).putPieceAtPosition(new Piece(color, BISHOP), new Position(color.pieceRow, column));
            }
            for (int column : new int[]{QUEEN_SIDE_KNIGHT_COLUMN, KING_SIDE_KNIGHT_COLUMN}) {
                verify(mockBoard).putPieceAtPosition(new Piece(color, KNIGHT), new Position(color.pieceRow, column));
            }
            verify(mockBoard).putPieceAtPosition(new Piece(color, KING), new Position(color.pieceRow, KING_COLUMN));
            verify(mockBoard).putPieceAtPosition(new Piece(color, QUEEN), new Position(color.pieceRow, QUEEN_COLUMN));
        }
    }
}
