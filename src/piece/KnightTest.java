package piece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Cell;

/**
 * @author Ravishankar P. Joshi
 * */
class KnightTest 
{
	private Knight knight;
	private Board board;
	@BeforeEach
	void setUp()
	{	
		board = new Board(); // this is a new empty board.
		knight = null;
	}

	@AfterEach
	void tearDown()
	{
		board = null;
		knight = null;
	}

	@Test
	void constructorTest() 
	{
		try 
		{
			Cell knightCell = board.getCellAt(Board.rowMin, Board.colMax);
			knight = new Knight(Board.White, knightCell);
			assertEquals(Board.White, knight.colour);
			assertEquals(knightCell, knight.currentPos);
			assertEquals(knight, knightCell.getPiece());
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
			Cell knightCell = board.getCellAt(Board.rowMin, Board.colMin);
			knight = new Knight(Board.White, knightCell);
			assertEquals(Board.White.charAt(0)+"N", knight.toString());
			
			knightCell.setPiece(null);
			knight = null;	//deleting the old knight.
		
			knight = new Knight(Board.Black, knightCell);
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
			knight = new Knight(Board.White, a1);
			ArrayList<Cell> allMoves = knight.getAllMoves(board);
			
			Cell c2 = board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+2));
			Cell b3 = board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+1));
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
	void movesFromNullBoardTest() 
	{
		try 
		{
			Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
			knight = new Knight(Board.White, a1);
			ArrayList<Cell> allMoves = knight.getAllMoves(null);
			assertEquals(null, allMoves,
				"Knight must return null moves on an empty board.");
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			System.out.println("Exception in movesFromNullBoardTest()"
					+ " of KnightTest");
		}
	}
	
	@Test
	void movesFromNormalCellTest() 
	{
		try 
		{
			Cell d4 = board.getCellAt((char)(Board.rowMin+3), 
					(char)(Board.colMin+3));
			knight = new Knight(Board.White, d4);
			ArrayList<Cell> allMoves = knight.getAllMoves(board);
			
			Cell[] validCells = 
			{
				board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+2)),//c2
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+1)),//b3
				board.getCellAt((char)(Board.rowMin+5), (char)(Board.colMin+2)),//c6
				board.getCellAt((char)(Board.rowMin+4), (char)(Board.colMin+1)),//b5
				
				board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+4)),//e2
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+5)),//f3
				board.getCellAt((char)(Board.rowMin+5), (char)(Board.colMin+4)),//e6
				board.getCellAt((char)(Board.rowMin+4), (char)(Board.colMin+5)),//f5
			};
			for(Cell c : validCells) 
			{
				assertTrue(allMoves.contains(c), 
					"Knight must be able to move from d4 to "+c);
			}
			assertEquals(8, allMoves.size(),
				"Knight can move to 8 cells from the cell d4.");
			
			
			Cell c2 = board.getCellAt((char)(Board.rowMin+1), 
					(char)(Board.colMin+2));
			Bishop b = new Bishop(Board.White, c2);
			//Creating a white bishop on c2, so that white knight can't attack it.
			for(Cell c: validCells)
			{	
				if(c==c2)
					assertFalse(knight.canMoveTo(c, board), 
						"A white knight can't attack a white bishop");
				else
				{
					assertTrue(allMoves.contains(c), 
						"Knight must be able to move from d4 to "+c);
				}
			}
			assertEquals(7, knight.moves.size());
			
			
			c2.setPiece(null);
			b = null;	//destructing the old bishop.
			
			b = new Bishop(Board.Black, c2);
			//Creating a black bishop on c2, so that a white knight can attack it.
			for(Cell c: validCells)
			{	
				if(c==c2)
					assertTrue(knight.canMoveTo(c, board), 
						"A white knight can attack a black bishop");
				else
				{
					assertTrue(allMoves.contains(c), 
						"Knight must be able to move from d4 to "+c);
				}
			}
			assertEquals(8, knight.moves.size());
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			System.out.println("Exception in movesFromNormalCellTest() of"
					+ " KnightTest");
		}
	}
	
	
}
