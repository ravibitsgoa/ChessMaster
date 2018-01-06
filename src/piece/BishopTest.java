package piece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.*;

class BishopTest {
	
	private Bishop bishop;
	private Board board;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		board = new Board();
		bishop = null;
	}

	@AfterEach
	void tearDown() 
	{
		board = null;
		bishop = null;
	}

	@Test 
	void constructorTest()
	{
		try 
		{
			Cell bishopCell = board.getCellAt(Board.rowMin, Board.colMin);
			bishop = new Bishop(Board.White, bishopCell);
			assertEquals(Board.White, bishop.colour);
			assertEquals(bishopCell, bishop.currentPos);
			assertEquals(bishop, bishopCell.getPiece());
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
			bishop = new Bishop(Board.White, 
					board.getCellAt(Board.rowMin, Board.colMin));
			//Made a new white bishop at cell A1 of the board.
			assertEquals(Board.White.charAt(0)+"B", bishop.toString(),
					"toString method of a white Bishop object should return WB");
			
			bishop = new Bishop(Board.Black, 
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
			bishop = new Bishop(Board.White, 
					board.getCellAt((char)(Board.rowMin+2), (char)(Board.colMin+3)));
			//Made a new white bishop at cell d3 of the board.
			
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
				board.getCellAt((char)(Board.rowMin+5), Board.colMin)			//a6
			};
			assertEquals(expected.length, bishop.getAllMoves(board).size());
			//Bishop must be able to move to only these cells.
			for(Cell move: expected)
				assertTrue(bishop.canMoveTo(move, board),
						"Bishop must be able to move to all these cells.");
			
			Cell queenCell = board.getCellAt((char)(Board.rowMin+4), (char)(Board.colMin+5));
			new Queen(Board.Black, queenCell);
			//Made a new black queen at cell f5 of the board.
			assertEquals(expected.length-2, bishop.getAllMoves(board).size());
			//Bishop must be able to move to only the cells up to the black queen.
			for(Cell move: expected)
			{	if(move.col <= queenCell.col)	//column<=f.
					assertTrue(bishop.canMoveTo(move, board),
						"Bishop must be able to move to the cells upto BQ.");
				else
					assertFalse(bishop.canMoveTo(move, board),
						"Bishop can't jump over a black queen.");
			}
			
			//destruct the black queen.
			queenCell.setPiece(null);
			
			new Queen(Board.White, queenCell);
			//Made a new *white* queen at cell f5 of the board.
			
			assertEquals(expected.length-3, bishop.getAllMoves(board).size());
			//Bishop must be able to move to only the cells before the white queen.
			
			for(Cell move: expected)
			{	if(move != queenCell && move.col <= queenCell.col)
					assertTrue(bishop.canMoveTo(move, board),
						"Bishop must be able to move to the cells upto WQ.");
				else
					assertFalse(bishop.canMoveTo(move, board),
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
