package chess;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import piece.*;

/**
 * @author Ravishankar P. Joshi
 * */
class CellTest 
{
	private Board board;
	
	@BeforeEach
	void setUp()
	{
		board = new Board();
	}

	@AfterEach
	void tearDown()
	{
		board = null;
	}

	@Test
	void constructorTest() 
	{	
		try
		{	
			Cell a1 = new Cell(Board.rowMin, Board.colMin);
			assertEquals(Board.rowMin, a1.row);
			assertEquals(Board.colMin, a1.col);
			assertEquals(null, a1.getPiece());
		}
		catch(Exception e)
		{
			System.out.println("Exception in constructorTest() of CellTest");
		}
		
		Executable closureContainingCodeToTest = 
				() -> new Cell((char)(Board.rowMin-1), Board.colMin);
		//a0 can't be a valid cell.
		assertThrows(Exception.class, closureContainingCodeToTest, 
				"Cell not empty exception should be thrown.");
	
		closureContainingCodeToTest = 
				() -> new Cell(Board.rowMin, (char)(Board.colMax+1));
		//i1 can't be a valid cell.
		assertThrows(Exception.class, closureContainingCodeToTest, 
				"Cell not empty exception should be thrown.");
	
		closureContainingCodeToTest = 
				() -> new Cell((char)(Board.rowMax+1), Board.colMin);
		//a9 can't be a valid cell.
		assertThrows(Exception.class, closureContainingCodeToTest, 
				"Cell not empty exception should be thrown.");
		
		closureContainingCodeToTest = 
				() -> new Cell(Board.rowMin, (char)(Board.colMin-1));
		//(a-1)1 can't be a valid cell.
		assertThrows(Exception.class, closureContainingCodeToTest, 
				"Cell not empty exception should be thrown.");

	}

	@Test
	void setPieceTest()
	{
		try
		{	
			Cell rookCell = board.getCellAt(Board.rowMin, Board.colMin);
			Rook wr = new Rook(Board.White, rookCell);
			assertTrue(rookCell.setPiece(wr), 
				"An empty Cell must return true on setting piece");
			assertTrue(rookCell.setPiece(null),
				"A cell must return true on being emptied.");	//cell emptied.
			assertTrue(rookCell.setPiece(wr),
				"An empty Cell must return true on setting piece");
			assertTrue(rookCell.setPiece(wr),
				"A cell must return true when setting same piece on it.");
			
			Cell queenCell = board.getCellAt(Board.rowMax, Board.colMin);
			Queen wq = new Queen(Board.White, queenCell);
			assertFalse(rookCell.setPiece(wq),
				"A cell must return false when a piece of same colour as"
				+ " one lying on it, is tried to set on it.");
			//You can't kill a white rook with a white queen.
			assertEquals(wr, rookCell.getPiece());
			//Rook must remain on the cell.
			
			queenCell = board.getCellAt(Board.rowMax, Board.colMax);
			Queen bq = new Queen(Board.Black, queenCell);
			assertTrue(rookCell.setPiece(bq),
				"A cell must return true when a piece of opposite colour"
				+ ", is tried to set on it.");
			//You can kill a white rook with a black queen.
			assertEquals(bq, rookCell.getPiece());
			//Black queen must remain on the cell.
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in setPieceTest() of CellTest");
		}
	}

	@Test
	void getPieceTest() 
	{
		try
		{	
			Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
			Rook rook = new Rook(Board.White, a1);
			
			a1.setPiece(rook);
			assertEquals(rook, a1.getPiece());	//Cell c must have the rook on it.
			
			a1.setPiece(null);
			rook = null;						//this rook is destructed now.
			assertEquals(null, a1.getPiece());	//c must not have anything.
			
			rook = new Rook(Board.White, a1);
			a1.setPiece(rook);
			assertEquals(rook, a1.getPiece());	//c must have the rook on it.
			
			rook.moveTo(board.getCellAt(Board.rowMax, Board.colMin), board);
			//On moving the rook from a1 to a8,
			//a1 must become empty.
			assertEquals(null, a1.getPiece());
			//and, a8 must have the rook on it.
			assertEquals(rook, 
					board.getCellAt(Board.rowMax, Board.colMin).getPiece());
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getPieceTest() of CellTest");
		}
	}

	@Test
	void toStringTest() 
	{
		Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
		assertEquals("a1", a1.toString(), "a1 cell must return its id correctly.");
		
		Cell h8 = board.getCellAt(Board.rowMax, Board.colMax);
		assertEquals("h8", h8.toString(), "h8 cell must return its id correctly.");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void testEqualsObject()
	{
		Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
		Cell a1DeepCopy = board.getCellAt(Board.rowMin, Board.colMin);
		assertTrue(a1.equals(a1DeepCopy));
		
		try
		{	
			Cell a1ShallowCopy = new Cell(Board.rowMin, Board.colMin);
			assertTrue(a1.equals(a1ShallowCopy));
			
			Rook r = new Rook(Board.Black, a1);
			assertFalse(a1.equals(r));
		}
		catch(Exception e)
		{
			System.out.println("Exception in testEqualsObject() of CellTest");
		}
	}

}
