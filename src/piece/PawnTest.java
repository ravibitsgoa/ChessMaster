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
	private Movement movement;
	
	@BeforeEach
	void setUp()
	{
		board  = new Board();
		movement =  board.getMovement();
		pawn = null;
	}

	@AfterEach
	void tearDown()
	{
		board = null;
		pawn = null;
		movement = null;
	}

	@Test
	void constructorTest() 
	{
		try 
		{
			Cell pawnCell = board.getCellAt(Board.rowMin, Board.colMax);
			pawn = new Pawn(Board.White, pawnCell);
			movement.construct(pawn, pawnCell);
			assertEquals(Board.White, pawn.colour);
			assertEquals(pawn, movement.getPieceOn(pawnCell));
			
			assertEquals(pawnCell, pawn.orig,
					"A pawn must store its original cell properly.");
			
			assertEquals(1, pawn.dir,
					"A white pawn moves only in direction 1.");
			
			board.kill(pawn);
			pawn = new Pawn(Board.Black, pawnCell);
			movement.construct(pawn, pawnCell);
			assertEquals(-1, pawn.dir,
					"A black pawn moves only in direction -1.");
			assertEquals(pawn, movement.getPieceOn(pawnCell));
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
			Cell origCell = board.getCellAt(Board.rowMin+1,	Board.colMin);
			//creating a white pawn on cell a2.
			Pawn pawn = new Pawn(Board.White, origCell);
			movement.construct(pawn, origCell);
			
			Cell a3 = board.getCellAt(Board.rowMin+2, Board.colMin);
			assertNotNull(movement.moveTo(pawn, a3));
			
			Cell a4 = board.getCellAt(Board.rowMin+3, Board.colMin);
			assertNotNull(movement.moveTo(pawn, a4));
			
			Cell a5 = board.getCellAt(Board.rowMin+4, Board.colMin);
			assertNotNull(movement.moveTo(pawn, a5));
			
			Cell a6 = board.getCellAt(Board.rowMin+5, Board.colMin);
			assertNotNull(movement.moveTo(pawn, a6));
			
			Cell a7 = board.getCellAt(Board.rowMin+6, Board.colMin);
			Cell a8 = board.getCellAt(Board.rowMin+7, Board.colMin);
			assertNull(movement.moveTo(pawn, a8), "A pawn can't jump "
					+ "from a6 to a8.");
			
			assertNotNull(movement.moveTo(pawn, a7));
			assertNotNull(movement.moveTo(pawn, a8));
			
			assertEquals("WQ", movement.getPieceOn(a8).toString(),
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
			Cell origCell = board.getCellAt(Board.rowMin+1, Board.colMin);
			//creating a white pawn on cell a2.
			pawn = new Pawn(Board.White, origCell);
			movement.construct(pawn, origCell);
			
			Cell a4 = board.getCellAt(Board.rowMin+3, Board.colMin);	
			//initial jump start.
			assertNotNull(movement.moveTo(pawn, a4));
			
			Cell a5 = board.getCellAt(Board.rowMin+4, Board.colMin);
			assertNotNull(movement.moveTo(pawn, a5));
			
			Cell a6 = board.getCellAt(Board.rowMin+5, Board.colMin);
			assertNotNull(movement.moveTo(pawn, a6));
			
			Cell a7 = board.getCellAt(Board.rowMin+6, Board.colMin);
			assertNotNull(movement.moveTo(pawn, a7));
			
			Cell a8 = board.getCellAt(Board.rowMin+7, Board.colMin);
			
			Queen queen = new Queen(Board.White);
			movement.construct(queen, a8);
			//Now, the pawn can't move to cell a8 blocked by a white queen.
			assertNull(movement.moveTo(pawn, a8));
			
			board.kill(queen);
			//destructing the white queen.
			//constructing a black queen on cell a8.
			movement.construct(new Queen(Board.Black), a8);
			//Now, the pawn can't move to it.
			assertNull(movement.moveTo(pawn, a8));
			
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
			Cell b7 = board.getCellAt(Board.rowMin+6, Board.colMin+1);
			pawn = new Pawn(Board.Black, b7);
			movement.construct(pawn, b7);
			
			Cell b6 = board.getCellAt(Board.rowMin+5, Board.colMin+1);
			assertNotNull(movement.moveTo(pawn, b6));
			
			Cell b5 = board.getCellAt(Board.rowMin+4, Board.colMin+1);
			assertNotNull(movement.moveTo(pawn, b5));

			Cell b4 = board.getCellAt(Board.rowMin+3, Board.colMin+1);
			assertNotNull(movement.moveTo(pawn, b4));

			Cell b3 = board.getCellAt(Board.rowMin+2, Board.colMin+1);
			assertNotNull(movement.moveTo(pawn, b3));
			
			Cell b2 = board.getCellAt(Board.rowMin+1, Board.colMin+1);
			assertNotNull(movement.moveTo(pawn, b2));
			
			Cell b1 = board.getCellAt(Board.rowMin, Board.colMin+1);
			assertNotNull(movement.moveTo(pawn, b1));
		}
		catch(Exception e)
		{
			System.out.println("Exception in moveToBlackPawnTest() of"
					+ " PawnTest.");
		}
	}
	
	@Test
	void moveToNullCellTest() 
	{
		try
		{
			Cell origCell = board.getCellAt(Board.rowMin+1,	Board.colMin);
			//creating a white pawn on cell a2.
			pawn = new Pawn(Board.White, origCell);
			movement.construct(pawn, origCell);
			
			assertNull(movement.moveTo(pawn, null));
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
			Cell origCell = board.getCellAt(Board.rowMin+1, Board.colMin);
			//creating a white pawn on cell a2.
			pawn = new Pawn(Board.White, origCell);
			movement.construct(pawn, origCell);
			
			ArrayList<Cell> moves = movement.getAllMoves(pawn);
			
			Cell a4 = board.getCellAt(Board.rowMin+3, Board.colMin);	
			//initial jump start.
			Cell a3 = board.getCellAt(Board.rowMin+2, Board.colMin);	
			//initial normal move.
			assertTrue(moves.contains(a3), 
					"A pawn can move from a2 to a3.");
			assertTrue(moves.contains(a4),
					"A pawn can move from a2 to a4 (initial move).");
			assertEquals(2, moves.size(),
					"A pawn can move to only 2 cells from cell a2.");
			
			Bishop bishop = new Bishop(Board.White);
			movement.construct(bishop, a4);
			moves = movement.getAllMoves(pawn);
			assertTrue(moves.contains(a3),
					"A pawn can move from a2 to a3.");
			assertFalse(moves.contains(a4),
					"A pawn can't move to an occupied cell linearly.");
			assertEquals(1, moves.size(),
					"A pawn can move to only 1 cell from cell a2,"
					+ " when a4 is occupied.");
			
			//destructing old bishop.
			board.kill(bishop);
			bishop= new Bishop(Board.Black);
			movement.construct(bishop, a4);
			moves = movement.getAllMoves(pawn);
			assertTrue(moves.contains(a3),
					"A pawn can move from a2 to a3.");
			assertFalse(moves.contains(a4),
					"A pawn can't move to an occupied cell linearly.");
			assertEquals(1, moves.size(),
					"A pawn can move to only 1 cell from cell a2,"
					+ " when a4 is occupied.");
			
			board.kill(bishop);
			bishop = new Bishop(Board.Black);
			movement.construct(bishop, a3);
			moves = movement.getAllMoves(pawn);
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
			Cell c2 = board.getCellAt(Board.rowMin+1, Board.colMin+2);
			pawn = new Pawn(Board.White, c2);
			movement.construct(pawn, c2);
			
			Cell d3 = board.getCellAt(Board.rowMin+2, Board.colMin+3);
			Cell b3 = board.getCellAt(Board.rowMin+2, Board.colMin+1);
			ArrayList<Cell> moves = movement.getAllMoves(pawn);
			assertFalse(moves.contains(d3), 
					"A pawn can't move from a2 to empty d3.");
			assertFalse(moves.contains(b3),
					"A pawn can't move from a2 to empty b3.");
			assertEquals(2, moves.size(),
					"A pawn can move to only 2 cells from cell a2.");
			
			Knight knight = new Knight(Board.Black);
			movement.construct(knight, b3);
			moves = movement.getAllMoves(pawn);
			assertFalse(moves.contains(d3), 
					"A pawn can't move from a2 to empty d3.");
			assertTrue(moves.contains(b3),
				"A white pawn can move from a2 to b3 having black"
				+ " knight.");
			assertEquals(3, moves.size(),
				"A pawn can move to only 3 cells from cell a2 now.");
			
			Bishop bishop = new Bishop(Board.White);
			movement.construct(bishop, d3);
			moves = movement.getAllMoves(pawn);
			assertFalse(moves.contains(d3), 
					"A white pawn can't attack a white bishop.");
			assertTrue(moves.contains(b3),
				"A white pawn can attack from a2 to b3 on a black"
				+ " knight.");
			assertEquals(3, moves.size(),
				"A pawn can move to only 3 cells from cell a2 now.");
			
			board.kill(bishop);
			bishop = new Bishop(Board.Black);
			movement.construct(bishop, d3);
			moves = movement.getAllMoves(pawn);
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
