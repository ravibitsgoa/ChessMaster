package piece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.*;

/**
 * @author Ravishankar P. Joshi
 * */
class PawnTest 
{
	private Pawn pawn;
	private Board board;
	
	@BeforeEach
	void setUp()
	{
		board  = new Board();
		pawn = null;
	}

	@AfterEach
	void tearDown()
	{
		board = null;
		pawn = null;
	}

	@Test
	void constructorTest() 
	{
		try 
		{
			Cell pawnCell = board.getCellAt(Board.rowMin, Board.colMax);
			pawn = new Pawn(Board.White, pawnCell);
			assertEquals(Board.White, pawn.colour);
			assertEquals(pawnCell, pawn.currentPos);
			assertEquals(pawn, pawnCell.getPiece());
			
			assertEquals(pawnCell, pawn.getOrig(),
					"A pawn must store its original cell properly.");
			
			assertEquals(1, pawn.getDir(),
					"A white pawn moves only in direction 1.");
			
			pawn = null;
			pawnCell.setPiece(null);
			pawn = new Pawn(Board.Black, pawnCell);
			assertEquals(-1, pawn.getDir(),
					"A black pawn moves only in direction -1.");
		} 
		catch (Exception e) 
		{
			System.out.println("Exception in constructorTest() of pawnTest");
		}
	}
	
	@Test
	void toStringTest() 
	{
		try 
		{
			Cell pawnCell = board.getCellAt(Board.rowMin, Board.colMin);
			pawn = new Pawn(Board.White, pawnCell);
			assertEquals(Board.White.charAt(0)+"P", pawn.toString());
			
			pawnCell.setPiece(null);
			pawn = null;	//deleting the old pawn.
		
			pawn = new Pawn(Board.Black, pawnCell);
			assertEquals(Board.Black.charAt(0)+"P", pawn.toString());
		} 
		catch (Exception e) 
		{
			System.out.println("Exception in toStringTest() of PawnTest");
		}
	}

	@Test
	void moveToWhitePawnTest() 
	{
		try
		{
			Cell origCell = board.getCellAt((char)(Board.rowMin+1), 
					Board.colMin);
			//creating a white pawn on cell a2.
			Pawn pawn = new Pawn(Board.White, origCell);
			
			Cell a3 = board.getCellAt((char)(Board.rowMin+2), 
					Board.colMin);
			assertTrue(pawn.moveTo(a3, board));
			
			Cell a4 = board.getCellAt((char)(Board.rowMin+3), 
					Board.colMin);
			assertTrue(pawn.moveTo(a4, board));
			
			Cell a5 = board.getCellAt((char)(Board.rowMin+4), 
					Board.colMin);
			assertTrue(pawn.moveTo(a5, board));
			
			Cell a6 = board.getCellAt((char)(Board.rowMin+5), 
					Board.colMin);
			assertTrue(pawn.moveTo(a6, board));
			
			Cell a7 = board.getCellAt((char)(Board.rowMin+6), 
					Board.colMin);
			Cell a8 = board.getCellAt((char)(Board.rowMin+7), 
					Board.colMin);
			assertFalse(pawn.moveTo(a8, board), "A pawn can't jump "
					+ "from a6 to a8.");
			
			assertTrue(pawn.moveTo(a7, board));
			assertTrue(pawn.moveTo(a8, board));
			
			assertEquals("WQ", a8.getPiece().toString(),
					"A pawn should be upgraded to a queen on reaching the"+
					"terminal row");
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Exception in moveToWhitePawnTest() of"
					+ " PawnTest");
		}
	}

	@Test
	void moveToWhitePawnFilledCellTest() 
	{
		try
		{
			Cell origCell = board.getCellAt((char)(Board.rowMin+1), 
					Board.colMin);
			//creating a white pawn on cell a2.
			pawn = new Pawn(Board.White, origCell);
			
			Cell a4 = board.getCellAt((char)(Board.rowMin+3), 
					Board.colMin);	//initial jump start.
			assertTrue(pawn.moveTo(a4, board));
			
			Cell a5 = board.getCellAt((char)(Board.rowMin+4), 
					Board.colMin);
			assertTrue(pawn.moveTo(a5, board));
			
			Cell a6 = board.getCellAt((char)(Board.rowMin+5), 
					Board.colMin);
			assertTrue(pawn.moveTo(a6, board));
			
			Cell a7 = board.getCellAt((char)(Board.rowMin+6), 
					Board.colMin);
			assertTrue(pawn.moveTo(a7, board));
			
			Cell a8 = board.getCellAt((char)(Board.rowMin+7), 
					Board.colMin);
			
			a8.setPiece(new Queen(Board.White, a8));
			//Now, the pawn can't move to cell a8 blocked by a white queen.
			assertFalse(pawn.moveTo(a8, board));
			
			a8.setPiece(null);	//destructing the white queen.
			//constructing a black queen on cell a8.
			a8.setPiece(new Queen(Board.Black, a8));
			//Now, the pawn can't move to it.
			assertFalse(pawn.moveTo(a8, board));
			
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Exception in "
				+ "moveToWhitePawnFilledCellTest() of PawnTest");
		}
	}
	
	@Test
	void moveToBlackPawnTest()
	{
		try
		{
			Cell b7 = board.getCellAt((char)(Board.rowMin+6), 
					(char)(Board.colMin+1));
			pawn = new Pawn(Board.Black, b7);
			
			Cell b6 = board.getCellAt((char)(Board.rowMin+5), 
					(char)(Board.colMin+1));
			assertTrue(pawn.moveTo(b6, board));
			
			Cell b5 = board.getCellAt((char)(Board.rowMin+4), 
					(char)(Board.colMin+1));
			assertTrue(pawn.moveTo(b5, board));

			Cell b4 = board.getCellAt((char)(Board.rowMin+3), 
					(char)(Board.colMin+1));
			assertTrue(pawn.moveTo(b4, board));

			Cell b3 = board.getCellAt((char)(Board.rowMin+2), 
					(char)(Board.colMin+1));
			assertTrue(pawn.moveTo(b3, board));
			
			Cell b2 = board.getCellAt((char)(Board.rowMin+1), 
					(char)(Board.colMin+1));
			assertTrue(pawn.moveTo(b2, board));
			
			Cell b1 = board.getCellAt(Board.rowMin, 
					(char)(Board.colMin+1));
			assertTrue(pawn.moveTo(b1, board));
		}
		catch(Exception e)
		{
			System.out.println("Exception in moveToBlackPawnTest() of"
					+ " PawnTest.");
		}
	}
	
	@Test
	void moveToNullBoardOrCellTest() 
	{
		try
		{
			Cell origCell = board.getCellAt((char)(Board.rowMin+1), 
					Board.colMin);
			//creating a white pawn on cell a2.
			pawn = new Pawn(Board.White, origCell);
			
			assertFalse(pawn.moveTo(null, board));
			
			Cell a8 = board.getCellAt((char)(Board.rowMin+7), 
					Board.colMin);
			assertFalse(pawn.moveTo(a8, null));
		}
		catch(Exception e)
		{
			System.out.println("Exception in moveToNullBoardOrCellTest()"
					+ " of PawnTest");
		}
	}
	
	@Test
	void normalMovesTest() 
	{
		try
		{
			Cell origCell = board.getCellAt((char)(Board.rowMin+1), 
					Board.colMin);
			//creating a white pawn on cell a2.
			pawn = new Pawn(Board.White, origCell);
			ArrayList<Cell> moves = pawn.getAllMoves(board);
			
			Cell a4 = board.getCellAt((char)(Board.rowMin+3), 
					Board.colMin);	//initial jump start.
			Cell a3 = board.getCellAt((char)(Board.rowMin+2), 
					Board.colMin);	//initial normal move.
			assertTrue(moves.contains(a3), 
					"A pawn can move from a2 to a3.");
			assertTrue(moves.contains(a4),
					"A pawn can move from a2 to a4 (initial move).");
			assertEquals(2, moves.size(),
					"A pawn can move to only 2 cells from cell a2.");
			
			
			a4.setPiece(new Bishop(Board.White, a4));
			moves = pawn.getAllMoves(board);
			assertTrue(moves.contains(a3),
					"A pawn can move from a2 to a3.");
			assertFalse(moves.contains(a4),
					"A pawn can't move to an occupied cell linearly.");
			assertEquals(1, moves.size(),
					"A pawn can move to only 1 cell from cell a2,"
					+ " when a4 is occupied.");
			
			
			a4.setPiece(null); //destructing old bishop.
			a4.setPiece(new Bishop(Board.Black, a4));
			moves = pawn.getAllMoves(board);
			assertTrue(moves.contains(a3),
					"A pawn can move from a2 to a3.");
			assertFalse(moves.contains(a4),
					"A pawn can't move to an occupied cell linearly.");
			assertEquals(1, moves.size(),
					"A pawn can move to only 1 cell from cell a2,"
					+ " when a4 is occupied.");
			
			a4.setPiece(null);
			a3.setPiece(new Bishop(Board.Black, a3));
			moves = pawn.getAllMoves(board);
			assertEquals(0, moves.size(),
					"A pawn can't move at all from initial position,"
					+"if the cell next to it, is blocked.");
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Exception in "
				+ "normalMovesTest() of PawnTest");
		}
	}
	
	@Test
	void attackMovesTest() 
	{
		try
		{
			Cell c2 = board.getCellAt((char)(Board.rowMin+1), 
					(char)(Board.colMin+2));
			pawn = new Pawn(Board.White, c2);
			
			Cell d3 = board.getCellAt((char)(Board.rowMin+2), 
					(char)(Board.colMin+3));
			Cell b3 = board.getCellAt((char)(Board.rowMin+2), 
					(char)(Board.colMin+1));
			ArrayList<Cell> moves = pawn.getAllMoves(board);
			assertFalse(moves.contains(d3), 
					"A pawn can't move from a2 to empty d3.");
			assertFalse(moves.contains(b3),
					"A pawn can't move from a2 to empty b3.");
			assertEquals(2, moves.size(),
					"A pawn can move to only 2 cells from cell a2.");
			
			b3.setPiece(new Knight(Board.Black, b3));
			moves = pawn.getAllMoves(board);
			assertFalse(moves.contains(d3), 
					"A pawn can't move from a2 to empty d3.");
			assertTrue(moves.contains(b3),
				"A white pawn can move from a2 to b3 having black"
				+ " knight.");
			assertEquals(3, moves.size(),
				"A pawn can move to only 3 cells from cell a2 now.");
			
			d3.setPiece(new Bishop(Board.White, d3));
			moves = pawn.getAllMoves(board);
			assertFalse(moves.contains(d3), 
					"A white pawn can't attack a white bishop.");
			assertTrue(moves.contains(b3),
				"A white pawn can attack from a2 to b3 on a black"
				+ " knight.");
			assertEquals(3, moves.size(),
				"A pawn can move to only 3 cells from cell a2 now.");
			
			d3.setPiece(null);
			d3.setPiece(new Bishop(Board.Black, d3));
			moves = pawn.getAllMoves(board);
			assertTrue(moves.contains(d3), 
					"A white pawn can attack a black bishop.");
			assertTrue(moves.contains(b3),
				"A white pawn can attack from a2 to b3 on a black"
				+ " knight.");
			assertEquals(4, moves.size(),
				"A pawn can move to only 3 cells from cell a2 now.");
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Exception in "
				+ "attackMovesTest() of PawnTest");
		}
	}
}
