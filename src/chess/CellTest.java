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
	private Movement movement;
	
	@BeforeEach
	void setUp()
	{
		board = new Board();
		movement = board.getMovement();
	}

	@AfterEach
	void tearDown()
	{
		board = null;
		movement = null;
	}

	@Test
	void constructorTest() 
	{	
		try
		{	
			Cell a1 = new Cell(Board.rowMin, Board.colMin);
			assertEquals(Board.rowMin, a1.row);
			assertEquals(Board.colMin, a1.col);
			assertEquals(null, movement.getPieceOn(a1));
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
			
			Rook r = new Rook(Board.Black);
			assertFalse(a1.equals(r));
		}
		catch(Exception e)
		{
			System.out.println("Exception in testEqualsObject() of CellTest");
		}
	}

	@Test
	void selectionTest()
	{
		Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
		assertFalse(a1.isSelected(), 
				"A newly created cell must not be in selected state.");
		a1.select(true);
		assertTrue(a1.isSelected(),
				"After selecting a cell, it must get into selected state.");
		a1.select(false);
		assertFalse(a1.isSelected(),
				"After deselecting a cell, it must not be in selected state.");
	}
	
	@Test
	void nextMoveTest()
	{
		Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
		assertFalse(a1.isNextMove(), 
				"A newly created cell must not be in next move state.");
		a1.setNextMove(true);
		assertTrue(a1.isNextMove(),
				"After setting a cell as next move, it must become so.");
		a1.setNextMove(false);
		assertFalse(a1.isNextMove(),
				"After reseting a cell from next move, it must not be so.");
	}
}
