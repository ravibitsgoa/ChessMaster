package piece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Cell;
import chess.Movement;

/**
 * @author Ravishankar P. Joshi
 * */
class KnightTest 
{
	private Knight knight;
	private Movement movement;
	private Board board;
	@BeforeEach
	void setUp()
	{	
		board = new Board(); // this is a new empty board.
		knight = null;
		movement = board.getMovement();
	}

	@AfterEach
	void tearDown()
	{
		board = null;
		knight = null;
		movement = null;
	}

	@Test
	void constructorTest() 
	{
		try 
		{
			Cell knightCell = board.getCellAt(Board.rowMin, Board.colMax);
			knight = new Knight(Board.White);
			movement.construct(knight, knightCell);
			assertEquals(Board.White, knight.colour);
			assertEquals(knight, movement.getPieceOn(knightCell));
		} 
		catch (Exception e) 
		{
			System.out.println("Exception in constructorTest() of KnightTest");
		}
	}
	
	@Test
	void toStringTest() 
	{
		try 
		{
			knight = new Knight(Board.White);
			assertEquals(Board.White.charAt(0)+"N", knight.toString());
			
			knight = null;	//deleting the old knight.
		
			knight = new Knight(Board.Black);
			assertEquals(Board.Black.charAt(0)+"N", knight.toString());
		} 
		catch (Exception e) 
		{
			System.out.println("Exception in toStringTest() of KnightTest");
		}
	}

	@Test
	void movesFromCornerTest() 
	{
		try 
		{
			Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
			knight = new Knight(Board.White);
			movement.construct(knight, a1);
			ArrayList<Cell> allMoves = movement.getAllMoves(knight);
			
			Cell c2 = board.getCellAt(Board.rowMin+1, Board.colMin+2);
			Cell b3 = board.getCellAt(Board.rowMin+2, Board.colMin+1);
			assertTrue(allMoves.contains(c2), 
				"Knight must be able to move from a1 to c2.");
			assertTrue(allMoves.contains(b3),
				"Knight must be able to move from a1 to b3.");
			assertEquals(2, allMoves.size(),
				"Knight can move to only 2 cells from corner cell a1.");
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			System.out.println("Exception in movesFromCornerTest() of"
					+ " KnightTest");
		}
	}
	
	@Test
	void movesFromNullCellTest() 
	{
		try 
		{
			knight = new Knight(Board.White);
			movement.construct(knight, null);
			ArrayList<Cell> allMoves = movement.getAllMoves(knight);
			assertNull(allMoves,
				"Knight must return null moves when its cell is null.");
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			System.out.println("Exception in movesFromNullCellTest()"
					+ " of KnightTest");
		}
	}
	
	@Test
	void movesFromNormalCellTest() 
	{
		try 
		{
			Cell d4 = board.getCellAt(Board.rowMin+3, Board.colMin+3);
			knight = new Knight(Board.White);
			movement.construct(knight, d4);
			ArrayList<Cell> allMoves = movement.getAllMoves(knight);
			
			Cell[] validCells = 
			{
				board.getCellAt(Board.rowMin+1, Board.colMin+2),//c2
				board.getCellAt(Board.rowMin+2, Board.colMin+1),//b3
				board.getCellAt(Board.rowMin+5, Board.colMin+2),//c6
				board.getCellAt(Board.rowMin+4, Board.colMin+1),//b5
				
				board.getCellAt(Board.rowMin+1, Board.colMin+4),//e2
				board.getCellAt(Board.rowMin+2, Board.colMin+5),//f3
				board.getCellAt(Board.rowMin+5, Board.colMin+4),//e6
				board.getCellAt(Board.rowMin+4, Board.colMin+5),//f5
			};
			for(Cell c : validCells) 
			{
				assertTrue(allMoves.contains(c), 
					"Knight must be able to move from d4 to "+c);
			}
			assertEquals(8, allMoves.size(),
				"Knight can move to 8 cells from the cell d4.");
			
			
			Cell c2 = board.getCellAt(Board.rowMin+1, Board.colMin+2);
			@SuppressWarnings("unused")
			Bishop bishop = new Bishop(Board.White);
			movement.construct(bishop, c2);
			allMoves = movement.getAllMoves(knight);
			//Creating a white bishop on c2, so that white knight can't attack it.
			for(Cell c: validCells)
			{	
				if(c.equals(c2))
					assertFalse(movement.canMoveTo(knight, c), 
						"A white knight can't attack a white bishop");
				else
				{
					assertTrue(allMoves.contains(c), 
						"Knight must be able to move from d4 to "+c);
				}
			}
			assertEquals(7, allMoves.size());
			
			board.kill(bishop);
			bishop = null;	//destructing the old bishop.
			
			bishop = new Bishop(Board.Black);
			movement.construct(bishop, c2);
			allMoves = movement.getAllMoves(knight);
			//Creating a black bishop on c2, so that a white knight can attack it.
			for(Cell c: validCells)
			{	
				if(c.equals(c2))
					assertTrue(movement.canMoveTo(knight, c), 
						"A white knight can attack a black bishop");
				else
				{
					assertTrue(allMoves.contains(c), 
						"Knight must be able to move from d4 to "+c);
				}
			}
			assertEquals(8, allMoves.size());
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			System.out.println("Exception in movesFromNormalCellTest() of"
					+ " KnightTest");
		}
	}
	
	
}
