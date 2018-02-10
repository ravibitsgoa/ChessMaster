package piece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.*;

/**
 * @author Ravishankar P. Joshi
 * */
class BishopTest {
	
	private Bishop bishop;
	private Board board;
	private Movement movement;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		board = new Board();
		movement = board.getMovement();
		bishop = null;
	}

	@AfterEach
	void tearDown() 
	{
		board = null;
		movement = null;
		bishop = null;
	}

	@Test 
	void constructorTest()
	{
		try 
		{
			Cell bishopCell = board.getCellAt(Board.rowMin, Board.colMin);
			bishop = new Bishop(Board.White);
			movement.construct(bishop, bishopCell);
			
			assertEquals(Board.White, bishop.colour);
			assertEquals(bishop, movement.getPieceOn(bishopCell));
		}
		catch(Exception e)
		{
			System.out.println("Exception in constructorTest() of BishopTest.");
		}
	}
	
	@Test
	void toStringTest() 
	{
		try 
		{
			bishop = new Bishop(Board.White);
			movement.construct(bishop, 
					board.getCellAt(Board.rowMin, Board.colMin));
			//Made a new white bishop at cell a1 of the board.
			assertEquals(Board.White.charAt(0)+"B", bishop.toString(),
					"toString method of a white Bishop object should return WB");
			
			bishop = new Bishop(Board.Black);
			movement.construct(bishop, 
					board.getCellAt(Board.rowMax, Board.colMax));
			//Made a new black bishop at cell h8 of the board.
			assertEquals(Board.Black.charAt(0)+"B", bishop.toString(),
					"toString method of black Bishop object should return BB");
		}
		catch(Exception e)
		{
			System.out.println("Exception in toStringTest() "
					+ "method of BishopTest.");
		}
	}

	@Test
	void getAllMovesTest() 
	{
		try 
		{
			bishop = new Bishop(Board.White);
			movement.construct(bishop, 
					board.getCellAt(Board.rowMin+2, Board.colMin+3));
			//Made a new white bishop at cell d3 of the board.
			
			Cell expected[]=
			{	
				board.getCellAt(Board.rowMin+3, Board.colMin+4),//e4
				board.getCellAt(Board.rowMin+4, Board.colMin+5),//f5
				board.getCellAt(Board.rowMin+5, Board.colMin+6),//g6
				board.getCellAt(Board.rowMin+6, Board.colMin+7),//h7
				
				board.getCellAt(Board.rowMin+1, Board.colMin+4),//e2
				board.getCellAt(Board.rowMin, Board.colMin+5),	//f1
				
				board.getCellAt(Board.rowMin+1, Board.colMin+2),//c2
				board.getCellAt(Board.rowMin, Board.colMin+1),	//b1
				
				board.getCellAt(Board.rowMin+3, Board.colMin+2),//c4
				board.getCellAt(Board.rowMin+4, Board.colMin+1),//b5
				board.getCellAt(Board.rowMin+5, Board.colMin)	//a6
			};
			assertEquals(expected.length, movement.getAllMoves(bishop).size());
			//Bishop must be able to move to only these cells.
			for(Cell move: expected)
				assertTrue(movement.canMoveTo(bishop, move),
						"Bishop must be able to move to all these cells.");
			
			Cell queenCell = board.getCellAt(Board.rowMin+4, Board.colMin+5);
			Queen queen = new Queen(Board.Black);
			movement.construct(queen, queenCell);
			//Made a new black queen at cell f5 of the board.
			assertEquals(expected.length-2, movement.getAllMoves(bishop).size());
			//Bishop must be able to move to only the cells up to the black queen.
			for(Cell move: expected)
			{	if(move.col <= queenCell.col)	//column<=f.
					assertTrue(movement.canMoveTo(bishop, move),
						"Bishop must be able to move to the cells upto BQ.");
				else
					assertFalse(movement.canMoveTo(bishop, move),
						"Bishop can't jump over a black queen.");
			}
			
			//destruct the black queen.
			board.kill(queen);
			
			queen = new Queen(Board.White);
			movement.construct(queen, queenCell);
			//Made a new *white* queen at cell f5 of the board.
			
			assertEquals(expected.length-3, movement.getAllMoves(bishop).size());
			//Bishop must be able to move to only the cells before the white queen.
			
			for(Cell move: expected)
			{	if(move != queenCell && move.col <= queenCell.col)
					assertTrue(movement.canMoveTo(bishop, move),
						"Bishop must be able to move to the cells upto WQ.");
				else
					assertFalse(movement.canMoveTo(bishop, move),
						"A white Bishop can't cross or attack a white queen.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in getAllMovesTest()"
					+ " method of BishopTest.");
		}
	}

	
}
