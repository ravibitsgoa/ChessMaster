package piece;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.*;

/**
 * @author Ravishankar P. Joshi
 * */
class KingTest 
{
	
	private Board board;
	private King king;
	private Movement movement;
	
	@BeforeEach
	void setUp()
	{	
		board = new Board();	// this is a new empty board.
		king = null;		
		movement = board.getMovement();
	}

	@AfterEach
	void tearDown() 
	{
		board = null;
		king = null;
		movement = null;
	}

	@Test
	void toStringTest() 
	{
		try 
		{
			king = new King(Board.White);
			//Made a new white king at cell a1 of the board.
			assertEquals(Board.White.charAt(0)+"K", king.toString(),
					"toString method of a white King object should return WK");
			
			king = new King(Board.Black);
			//Made a new white king at cell h8 of the board.
			assertEquals(Board.Black.charAt(0)+"K", king.toString(),
					"toString method of black King object should return BK");
		}
		catch(Exception e)
		{
			System.out.println("Exception in toStringTest() "
					+ "method of KingTest");
		}
	}
	
	@Test
	void getColourTest() 
	{
		try
		{
			king = new King(Board.White);
			//Made a new white king at cell A1 of the board.
			assertEquals(Board.White, king.getColour(),
					"getColour method of a white King object should return White");
			
			king = new King(Board.Black);
			//Made a new black king at cell h8 of the board.
			assertEquals(Board.Black, king.getColour(),
					"getColour method of a black King object should return Black");
		}
		catch(Exception e)
		{
			System.out.println("Exception in getColourTest() "
					+ "method of KingTest.");
		}
	}
	
	@Test
	void movesUnderAttackTest()
	{
		try 
		{
			king = new King(Board.White);
			movement.construct(king, board.getCellAt(Board.rowMin, Board.colMin));
			//Made a new white king at cell a1 of the board.
			
			Cell rookCell = board.getCellAt(Board.rowMin+7, Board.colMin+1);
			@SuppressWarnings("unused")
			Rook rook = new Rook(Board.Black);
			movement.construct(rook, rookCell);
			
			//board.print();
			
			//king must be able to move to only the cell a2.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(Board.rowMin+1, Board.colMin)));
			//ArrayList<Cell> moves = movement.getAllMoves(king);
			//for(Cell c: moves)
			//	System.out.println(c);
			assertEquals(1, movement.getAllMoves(king).size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in movesUnderAttackTest() "
					+ "method of KingTest.");
		}
	}
	
	@Test
	void movesFromCornerTest()
	{
		try 
		{
			king = new King(Board.White);
			movement.construct(king, board.getCellAt(Board.rowMin, Board.colMin));
			//Made a new white king at cell a1 of the board.
			
			//king must be able to move to all 3 cells just beside him.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(Board.rowMin+1, Board.colMin)));
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(Board.rowMin, Board.colMin+1)));
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(Board.rowMin+1, Board.colMin+1)));
			assertEquals(3, movement.getAllMoves(king).size());
		}
		catch(Exception e)
		{
			System.out.println("Exception in movesFromCornerTest() "
					+ "method of KingTest.");
		}
	}
	
	@Test
	void movesFromTerminalRowTest()
	{
		try
		{
			Cell pos = board.getCellAt(Board.rowMin, Board.colMin+1);
			king = new King(Board.White);
			movement.construct(king, pos);
			//Made a new white king at cell b1 of the board.
			
			//King must be able to move to all 5 cells just beside him.
			//This is b2.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row+1, pos.col)));
			//This is c1.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row, pos.col+1)));
			//This is a1.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row, pos.col-1)));
			//This is c2.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row+1, pos.col+1)));
			//This is a2.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row+1, pos.col-1)));
			
			assertEquals(5, movement.getAllMoves(king).size());
		}
		catch(Exception e)
		{
			System.out.println("Exception in movesFromTerminalRowTest() "
					+ "method of KingTest.");	
		}
	}
	
	@Test
	void movesFromTerminalColTest()
	{
		try 
		{
			Cell pos = board.getCellAt(Board.rowMin+1, Board.colMin);
			king = new King(Board.White);
			movement.construct(king, pos);
			//Made a new white king at cell a2 of the board.
			
			//King must be able to move to all 5 cells just beside him.
			//This is a1.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row-1, pos.col)));
			//This is a3.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row+1, pos.col)));
			//This is b1.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row-1, pos.col+1)));
			//This is b2.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row, pos.col+1)));
			//This is b3.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row+1, pos.col+1)));
			
			assertEquals(5, movement.getAllMoves(king).size());
		}
		catch(Exception e)
		{
			System.out.println("Exception in movesFromTerminalColTest() "
					+ "method of KingTest.");	
		}
	}
	
	@Test
	void movesFromNormalCellTest()
	{
		try
		{
			Cell pos = board.getCellAt(Board.rowMin+1, Board.colMin+1);
			king = new King(Board.White);
			movement.construct(king, pos);
			//Made a new white king at cell b2 of the board.
			
			//King must be able to move to all 8 cells just beside him.
			//This is a1.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row-1, pos.col-1)));
			//This is b1.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row-1, pos.col)));
			//This is c1.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row-1, pos.col+1)));
			//This is a2.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row, pos.col-1)));
			//This is a3.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row+1, pos.col-1)));
			//This is c2.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row, pos.col+1)));
			//This is c3.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row+1, pos.col+1)));
			//This is b1.
			assertTrue(movement.canMoveTo(king,
				board.getCellAt(pos.row+1, pos.col)));
					
			assertEquals(8, movement.getAllMoves(king).size());
		}
		catch(Exception e)
		{
			System.out.println("Exception in movesFromNormalCellTest() "
					+ "method of KingTest.");	
		}
	}

}
