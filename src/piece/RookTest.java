package piece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import chess.*;

/**
 * jUnit test class for class {@link:piece.Rook}
 * @author Ravishankar P. Joshi
 */

class RookTest {

	private Board board;
	private Rook rook;
	/**
	 */
	@BeforeEach
	void setUp()
	{
		board = new Board();
		rook = null;
	}

	/**
	 */
	@AfterEach
	void tearDown()
	{
		board = null;
		rook = null;
	}

	/**
	 * Test method for {@link piece.Rook#toString()}.
	 */
	@Test
	void toStringTest() 
	{
		try 
		{
			rook = new Rook(Board.White, board.getCellAt(Board.rowMin, Board.colMin));
			assertEquals(Board.White.charAt(0)+"R", rook.toString(),
					"A white rook must return WR from toString()");
			
			rook = new Rook(Board.Black, board.getCellAt(Board.rowMax, Board.colMax));
			assertEquals(Board.Black.charAt(0)+"R", rook.toString(),
					"A black rook must return BR from toString()");
		}
		catch(Exception e)
		{
			System.out.println("Exception in toStringTest() of RookTest");
		}
	}

	/**
	 * Test method for {@link piece.Rook#getAllMoves(chess.Board)}.
	 */
	@Test
	void getAllMovesTest() 
	{
		try 
		{
			rook = new Rook(Board.White, 
					board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+2)));
			//Made a new white rook at cell c3 of the board.
			
			Cell expected[]=
			{	
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+0)),//a3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+1)),//b3
				
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+3)),//d3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+4)),//e3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+5)),//f3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+6)),//g3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+7)),//h3
				
				board.getCellAt((char)(Board.rowMin+0), (char)(Board.colMin+2)),//c1
				board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+2)),//c2
				
				board.getCellAt((char)(Board.rowMin+3), (char)(Board.colMin+2)),//c4
				board.getCellAt((char)(Board.rowMin+4), (char)(Board.colMin+2)),//c5
				board.getCellAt((char)(Board.rowMin+5), (char)(Board.colMin+2)),//c6
				board.getCellAt((char)(Board.rowMin+6), (char)(Board.colMin+2)),//c7
				board.getCellAt((char)(Board.rowMin+7), (char)(Board.colMin+2)),//c8
			};
			assertEquals(expected.length, rook.getAllMoves(board).size());
			//rook must be able to move to only these cells.
			for(Cell move: expected)
				assertTrue(rook.canMoveTo(move, board),
						"rook must be able to move to all these cells.");
			
			Cell pawnCell = board.getCellAt((char)(Board.rowMin+2), 
					(char)(Board.colMin+5));
			new Pawn(Board.Black, pawnCell);
			//Made a new black pawn at cell f3 of the board.
			
			assertEquals(expected.length-2, rook.getAllMoves(board).size());
			//rook must be able to move to only the cells up to the black pawn.
			
			for(Cell move: expected)
			{	if(move.col <= pawnCell.col)	//column<=f.
					assertTrue(rook.canMoveTo(move, board),
						"rook must be able to move to the cells upto BP.");
				else
					assertFalse(rook.canMoveTo(move, board),
						"rook can't jump over a black pawn.");
			}
			
			//destruct the black pawn.
			pawnCell.setPiece(null);
			
			new Pawn(Board.White, pawnCell);
			//Made a new *white* pawn at cell f3 of the board.
			
			assertEquals(expected.length-3, rook.getAllMoves(board).size());
			//rook must be able to move to only the cells before the white pawn.
			
			for(Cell move: expected)
			{	if(move.col < pawnCell.col)
					assertTrue(rook.canMoveTo(move, board),
						"rook must be able to move to the cells upto the WP.");
				else
					assertFalse(rook.canMoveTo(move, board),
						"A white rook can't cross or attack a white pawn.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in getAllMovesTest() method of RookTest.");
		}
	}

}
