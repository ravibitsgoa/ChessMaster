/**
 * 
 */
package piece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.*;

/**
 * jUnit test class for class {@link piece.Queen}.
 * @author Ravishankar P. Joshi
 **/
class QueenTest 
{
	private Board board;
	private Queen queen;
	private Movement movement;
	
	@BeforeEach
	void setUp() 
	{	
		board = new Board();
		queen = null;
		movement = board.getMovement();
	}

	@AfterEach
	void tearDown()
	{
		board = null;
		queen = null;
		movement = null;
	}

	/**
	 * Test method for 
	 * {@link piece.Queen#Queen(java.lang.String, chess.Cell)}.
	 * */
	@Test 
	void constructorTest()
	{
		try 
		{
			Cell queenCell = board.getCellAt(Board.rowMin, Board.colMin);
			queen = new Queen(Board.White);
			//made a white queen on cell a1.
			movement.construct(queen, queenCell);
			
			assertEquals(Board.White, queen.colour);
			assertEquals(queen, movement.getPieceOn(queenCell));
			
			board.kill(queen);
			queenCell = board.getCellAt(Board.rowMax, Board.colMin);
			queen = new Queen(Board.Black);
			//made a black queen on cell a8.
			movement.construct(queen, queenCell);
			assertEquals(Board.Black, queen.colour);
			assertEquals(queen, movement.getPieceOn(queenCell));
		}
		catch(Exception e)
		{
			System.out.println("Exception in constructorTest() of"
					+ " QueenTest.");
		}
	}
	
	/**
	 * Test method for {@link piece.Queen#toString()}.
	 **/
	@Test
	void toStringTest() 
	{
		try 
		{
			queen = new Queen(Board.White);
			//Made a new white queen.
			assertEquals(Board.White.charAt(0)+"Q", queen.toString(),
				"toString method of a white queen object should return WQ");
			
			queen = new Queen(Board.Black);
			//Made a new black queen.
			assertEquals(Board.Black.charAt(0)+"Q", queen.toString(),
				"toString method of black queen object should return BQ");
		}
		catch(Exception e)
		{
			System.out.println("Exception in toStringTest() "
				+ "method of QueenTest.");
		}
	}


	/**
	 * Test method for {@link piece.Queen#getAllMoves(chess.Board)}.
	 */
	@Test
	void getAllMovesTest() 
	{
		try 
		{
			queen = new Queen(Board.White);
			movement.construct(queen, board.getCellAt(Board.rowMin+2,
								Board.colMin+3));
			//Made a new white queen at cell d3 of the board.
			
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
				board.getCellAt(Board.rowMin+5, Board.colMin),	//a6
				
				board.getCellAt(Board.rowMin+2, Board.colMin),	//a3
				board.getCellAt(Board.rowMin+2, Board.colMin+1),//b3
				board.getCellAt(Board.rowMin+2, Board.colMin+2),//c3
				
				board.getCellAt(Board.rowMin+2, Board.colMin+4),//e3
				board.getCellAt(Board.rowMin+2, Board.colMin+5),//f3
				board.getCellAt(Board.rowMin+2, Board.colMin+6),//g3
				board.getCellAt(Board.rowMin+2, Board.colMin+7),//h3
				
				board.getCellAt(Board.rowMin, Board.colMin+3),	//d1
				board.getCellAt(Board.rowMin+1, Board.colMin+3),//d2
				
				board.getCellAt(Board.rowMin+3, Board.colMin+3),//d4
				board.getCellAt(Board.rowMin+4, Board.colMin+3),//d5
				board.getCellAt(Board.rowMin+5, Board.colMin+3),//d6
				board.getCellAt(Board.rowMin+6, Board.colMin+3),//d7
				board.getCellAt(Board.rowMin+7, Board.colMin+3),//d8
			};
			ArrayList<Cell> actualQueenMoves = movement.getAllMoves(queen);
			assertEquals(expected.length, actualQueenMoves.size());
			//Queen must be able to move to only these cells.
			for(Cell move: expected)
				assertTrue(actualQueenMoves.contains(move),
						"Queen must be able to move to all these cells.");
			
			Cell rookCell = board.getCellAt(Board.rowMin+4, 
					Board.colMin+5);
			Rook rook = new Rook(Board.Black);
			movement.construct(rook, rookCell);
			//Made a new black rook at cell f5 of the board.
			actualQueenMoves = movement.getAllMoves(queen);
			assertEquals(expected.length-2, actualQueenMoves.size());
			//White queen must be able to move to only the cells up to
			//the black rook.
			
			Cell g6 = board.getCellAt(Board.rowMin+5, Board.colMin+6);
			Cell h7 = board.getCellAt(Board.rowMin+6, Board.colMin+7);
					
			for(Cell move: expected)
			{	if(move!=g6 && move!=h7)
					assertTrue(actualQueenMoves.contains(move),
						"WQ must be able to move to the cells upto BR.");
				else
					assertFalse(actualQueenMoves.contains(move),
						"White Queen can't jump over a black rook.");
			}
			
			//destruct the black rook.
			board.kill(rook);
			
			rook = new Rook(Board.White);
			movement.construct(rook, rookCell);
			//Made a new *white* rook at cell f5 of the board.
			
			actualQueenMoves = movement.getAllMoves(queen);
			assertEquals(expected.length-3, actualQueenMoves.size());
			//Queen must be able to move to only the cells before the white rook.
			
			for(Cell move: expected)
			{	if(move != rookCell && move!=g6 && move!=h7)
					assertTrue(actualQueenMoves.contains(move),
						"WQ must be able to move to the cells upto WR.");
				else
					assertFalse(actualQueenMoves.contains(move),
						"A white Queen can't cross or attack a white rook.");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in getAllMovesTest() "
					+ "method of QueenTest.");
		}
	}

}
