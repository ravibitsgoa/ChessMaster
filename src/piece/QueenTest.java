/**
 * 
 */
package piece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.*;

/**
 * jUnit test class for class {@link piece.Queen}.
 * @author Ravishankar P. Joshi
 **/
class QueenTest {

	private Board board;
	private Queen queen;
	
	@BeforeEach
	void setUp() 
	{	
		board = new Board();
		queen = null;
	}

	@AfterEach
	void tearDown()
	{
		board = null;
		queen = null;
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
			queen = new Queen(Board.White, queenCell);
			assertEquals(Board.White, queen.colour);
			assertEquals(queenCell, queen.currentPos);
			assertEquals(queen, queenCell.getPiece());
			
			queenCell = board.getCellAt(Board.rowMax, Board.colMin);
			queen = new Queen(Board.Black, queenCell);
			assertEquals(Board.Black, queen.colour);
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
			queen = new Queen(Board.White, 
					board.getCellAt(Board.rowMin, Board.colMin));
			//Made a new white queen at cell A1 of the board.
			assertEquals(Board.White.charAt(0)+"Q", queen.toString(),
				"toString method of a white queen object should return WQ");
			
			queen = new Queen(Board.Black, 
					board.getCellAt(Board.rowMax, Board.colMax));
			//Made a new black queen at cell h8 of the board.
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
			queen = new Queen(Board.White, 
					board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+3)));
			//Made a new white queen at cell d3 of the board.
			
			Cell expected[]=
			{	
				board.getCellAt((char)(Board.rowMin+3), (char)(Board.colMin+4)),//e4
				board.getCellAt((char)(Board.rowMin+4), (char)(Board.colMin+5)),//f5
				board.getCellAt((char)(Board.rowMin+5), (char)(Board.colMin+6)),//g6
				board.getCellAt((char)(Board.rowMin+6), (char)(Board.colMin+7)),//h7
				
				board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+4)),//e2
				board.getCellAt(Board.rowMin, (char)(Board.colMin+5)),			//f1
				
				board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+2)),//c2
				board.getCellAt(Board.rowMin, (char)(Board.colMin+1)),			//b1
				
				board.getCellAt((char)(Board.rowMin+3), (char)(Board.colMin+2)),//c4
				board.getCellAt((char)(Board.rowMin+4), (char)(Board.colMin+1)),//b5
				board.getCellAt((char)(Board.rowMin+5), Board.colMin),			//a6
				
				board.getCellAt((char)(Board.rowMin+2), Board.colMin),			//a3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+1)),//b3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+2)),//c3
				
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+4)),//e3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+5)),//f3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+6)),//g3
				board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+7)),//h3
				
				board.getCellAt(Board.rowMin, (char)(Board.colMin+3)),			//d1
				board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+3)),//d2
				
				board.getCellAt((char)(Board.rowMin+3), (char)(Board.colMin+3)),//d4
				board.getCellAt((char)(Board.rowMin+4), (char)(Board.colMin+3)),//d5
				board.getCellAt((char)(Board.rowMin+5), (char)(Board.colMin+3)),//d6
				board.getCellAt((char)(Board.rowMin+6), (char)(Board.colMin+3)),//d7
				board.getCellAt((char)(Board.rowMin+7), (char)(Board.colMin+3)),//d8
			};
			assertEquals(expected.length, queen.getAllMoves(board).size());
			//Queen must be able to move to only these cells.
			for(Cell move: expected)
				assertTrue(queen.canMoveTo(move, board),
						"Queen must be able to move to all these cells.");
			
			Cell rookCell = board.getCellAt((char)(Board.rowMin+4), 
					(char)(Board.colMin+5));
			new Rook(Board.Black, rookCell);
			//Made a new black rook at cell f5 of the board.
			assertEquals(expected.length-2, queen.getAllMoves(board).size());
			//White queen must be able to move to only the cells up to
			//the black rook.
			
			Cell g6 = board.getCellAt((char)(Board.rowMin+5), 
					(char)(Board.colMin+6));
			Cell h7 = board.getCellAt((char)(Board.rowMin+6), 
					(char)(Board.colMin+7));
					
			for(Cell move: expected)
			{	if(move!=g6 && move!=h7)
					assertTrue(queen.canMoveTo(move, board),
						"WQ must be able to move to the cells upto BR.");
				else
					assertFalse(queen.canMoveTo(move, board),
						"White Queen can't jump over a black rook.");
			}
			
			//destruct the black rook.
			rookCell.setPiece(null);
			
			new Rook(Board.White, rookCell);
			//Made a new *white* rook at cell f5 of the board.
			
			assertEquals(expected.length-3, queen.getAllMoves(board).size());
			//Queen must be able to move to only the cells before the white rook.
			
			for(Cell move: expected)
			{	if(move != rookCell && move!=g6 && move!=h7)
					assertTrue(queen.canMoveTo(move, board),
						"WQ must be able to move to the cells upto WR.");
				else
					assertFalse(queen.canMoveTo(move, board),
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
