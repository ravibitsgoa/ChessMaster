package piece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import chess.*;

/**
 * jUnit test class for class {@link piece.Rook}.
 * @author Ravishankar P. Joshi
 */

class RookTest 
{
	private Board board;
	private Movement movement;
	private Rook rook;
	
	@BeforeEach
	void setUp()
	{
		board = new Board();
		rook = null;
		movement = board.getMovement();
	}

	/**
	 */
	@AfterEach
	void tearDown()
	{
		board = null;
		rook = null;
		movement = null;
	}

	/**
	 * Test method for {@link piece.Rook#toString()}.
	 */
	@Test
	void toStringTest() 
	{
		try 
		{
			rook = new Rook(Board.White);
			assertEquals(Board.White.charAt(0)+"R", rook.toString(),
					"A white rook must return WR from toString()");
			
			rook = new Rook(Board.Black);
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
			rook = new Rook(Board.White);
			movement.construct(rook, board.getCellAt(Board.rowMin+2, Board.colMin+2));
			//Made a new white rook at cell c3 of the board.
			
			Cell expected[]=
			{	
				board.getCellAt(Board.rowMin+2, Board.colMin+0),//a3
				board.getCellAt(Board.rowMin+2, Board.colMin+1),//b3
				
				board.getCellAt(Board.rowMin+2, Board.colMin+3),//d3
				board.getCellAt(Board.rowMin+2, Board.colMin+4),//e3
				board.getCellAt(Board.rowMin+2, Board.colMin+5),//f3
				board.getCellAt(Board.rowMin+2, Board.colMin+6),//g3
				board.getCellAt(Board.rowMin+2, Board.colMin+7),//h3
				
				board.getCellAt(Board.rowMin+0, Board.colMin+2),//c1
				board.getCellAt(Board.rowMin+1, Board.colMin+2),//c2
				
				board.getCellAt(Board.rowMin+3, Board.colMin+2),//c4
				board.getCellAt(Board.rowMin+4, Board.colMin+2),//c5
				board.getCellAt(Board.rowMin+5, Board.colMin+2),//c6
				board.getCellAt(Board.rowMin+6, Board.colMin+2),//c7
				board.getCellAt(Board.rowMin+7, Board.colMin+2),//c8
			};
			ArrayList<Cell> actualRookMoves = movement.getAllMoves(rook);
			assertEquals(expected.length, actualRookMoves.size());
			//rook must be able to move to only these cells.
			for(Cell move: expected)
				assertTrue(actualRookMoves.contains(move),
						"rook must be able to move to cell "+move);
			
			Cell pawnCell = board.getCellAt(Board.rowMin+2, 
					Board.colMin+5);
			Pawn pawn = new Pawn(Board.Black, pawnCell);
			movement.construct(pawn, pawnCell);
			//Made a new black pawn at cell f3 of the board.
			
			actualRookMoves = movement.getAllMoves(rook);
			assertEquals(expected.length-2, actualRookMoves.size());
			//rook must be able to move to only the cells up to the black pawn.
			
			for(Cell move: expected)
			{	if(move.col <= pawnCell.col)	//column<=f.
					assertTrue(actualRookMoves.contains(move),
						"rook must be able to move to the cells upto BP.");
				else
					assertFalse(actualRookMoves.contains(move),
						"rook can't jump over a black pawn.");
			}
			
			//destruct the black pawn.
			board.kill(pawn);
			
			pawn = new Pawn(Board.White, pawnCell);
			movement.construct(pawn, pawnCell);
			//Made a new *white* pawn at cell f3 of the board.
			
			actualRookMoves = movement.getAllMoves(rook);
			assertEquals(expected.length-3, actualRookMoves.size());
			//rook must be able to move to only the cells before the white pawn.
			
			for(Cell move: expected)
			{	if(move.col < pawnCell.col)
					assertTrue(actualRookMoves.contains(move),
						"rook must be able to move to the cells upto the WP.");
				else
					assertFalse(actualRookMoves.contains(move),
						"A white rook can't cross or attack a white pawn.");
			}
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Exception in getAllMovesTest() method of RookTest.");
		}
	}

}
