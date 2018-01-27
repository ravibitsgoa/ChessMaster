/** 
 * Unit tests for the abstract class Piece.
 */
package piece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import chess.*;

/**
 * @author Ravishankar P. Joshi
 * */
class PieceTest 
{
	private Piece piece;
	private Board board;
	@BeforeEach
	void setUp()
	{
		piece = null;
		board = new Board();
	}

	@AfterEach
	void tearDown()
	{
		piece = null;
		board = null;
	}

	/**
	 * Test method for the constructor of Piece class
	 * piece.Piece()
	 * */
	
	@Test
	void constructorTest()
	{
		Cell a1= board.getCellAt(Board.rowMin, Board.colMin);
		
		try 
		{
			piece = new Rook(Board.Black, a1);
			assertEquals(Board.Black, piece.colour);
			assertEquals(a1, piece.currentPos);
			assertEquals(piece, a1.getPiece());
			assertEquals(null, piece.moves);
		}
		catch(Exception e)
		{
			System.out.println("Exception in method constructorTest() "
					+ "of PieceTest");
		}
		
		Executable closureContainingCodeToTest = () -> piece = 
				new Rook(Board.White, a1);
		assertThrows(Exception.class, closureContainingCodeToTest, 
				"Cell not empty exception should be thrown.");
		
		closureContainingCodeToTest = () -> piece = 
				new Rook(Board.White, null);
		assertThrows(Exception.class, closureContainingCodeToTest, 
				"Null cell exception should be thrown.");
	
		closureContainingCodeToTest = () -> piece = 
				new Rook(null, board.getCellAt(Board.rowMax, Board.colMax));
		assertThrows(Exception.class, closureContainingCodeToTest, 
				"Null colour exception should be thrown.");
	}
	
	/**
	 * Test method for concrete method
	 * piece.Piece.moveTo(chess.Cell, chess.Board).
	 * */
	@Test
	void moveToTest() 
	{
		try
		{
			//Creating a new black rook at the cell a1.
			piece = new Rook(Board.Black, board.getCellAt(Board.rowMin, Board.colMin));
			board.addPiece(piece);
			Cell dest= board.getCellAt((char)(Board.rowMin+2), Board.colMin);
			
			assertFalse(piece.moveTo(dest, null),
					"A piece must return false when board is null.");
			assertFalse(piece.moveTo(null, board),
					"A piece must return false when destination is null.");
			//board.print();
			//System.out.println(dest);
			assertTrue(piece.moveTo(dest, board), 
					"rook must be able to move to cell a3 from a1.");
			
			assertEquals(null, board.colourAt(Board.rowMin, Board.colMin), 
					"a1 cell must be empty now.");
			
			assertEquals(piece, dest.getPiece(), 
					"rook must be on the dest cell now.");
			assertEquals(dest, piece.currentPos, 
					"rook must be on the dest cell now.");
			
			assertEquals(14, piece.moves.size(), 
					"rook must have 14 moves from cell a3.");
			
			ArrayList<Cell> moveslist = piece.getAllMoves(board);
			dest= board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+1));
			assertEquals(false, piece.moveTo(dest, board), 
					"rook must not be able to move b2 from a3.");
			assertEquals(moveslist, piece.moves, 
					"the list of moves must not change.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in moveToTest() "
					+ "method of PieceTest");
		}
	}

	/**
	 * Test method for concrete method
	 * piece.Piece.canMoveTo(chess.Cell, chess.Board).
	 * 
	 * Tests all the moves of the rook from the cell a1 of a rook, 
	 * when all other cells are empty.
	 * */
	@Test
	void canMoveToTest1() 
	{
		try 
		{
			//Creating a new black rook at the cell a1.
			piece = new Rook(Board.Black, board.getCellAt(Board.rowMin, Board.colMin));
			assertEquals(null, piece.moves);

			Cell dest = board.getCellAt(Board.rowMax, Board.colMin);
			assertFalse(piece.canMoveTo(dest, null),
					"A piece must return false when board is null.");
			assertFalse(piece.canMoveTo(null, board),
					"A piece must return false when destination is null.");
			
			//Make sure that initially moves list is empty,
			//and, on calling canMoveTo(), it fills the list.
			Boolean x = piece.canMoveTo(dest, board);
			assertEquals(14, piece.moves.size());
			assertEquals(true, x);	
		}
		catch(Exception e)
		{
			System.out.println("Exception in testCanMoveTo1() "
					+ "method of PieceTest");
		}
	}
	
	/**
	 * Tests the moves of a rook from the cell a1 when all other cells are empty.
	 **/
	@Test
	void canMoveToTest2()
	{
		try 
		{
			//Creating a new black rook at the cell a1.
			piece = new Rook(Board.White, board.getCellAt(Board.rowMin, Board.colMin));
			ArrayList<Cell> temp = piece.getAllMoves(board);
			//Make sure that canMoveTo() doesn't mess with 
			//already filled list of moves.
			assertEquals(14, piece.moves.size());
			assertEquals(temp, piece.moves);
			Boolean y = piece.canMoveTo(
					board.getCellAt(Board.rowMax, Board.colMin), board);
			assertEquals(true, y);
			Boolean selfCell = piece.canMoveTo(
					board.getCellAt(Board.rowMin, Board.colMin), board);
			assertEquals(false, selfCell);
		}
		catch(Exception e)
		{
			System.out.println("Exception in testCanMoveTo2() "
					+ "method PieceTest");
		}
	}

	/**
	 * Test method for concrete method
	 * piece.Piece.movesInDir(chess.Board, int, int).
	 * It should work independent of the type of piece.
	 **/
	@Test
	void rookMovesFromCornerTest() 
	{
		try 
		{
			//Creating a new black rook at the cell a1.
			piece = new Rook(Board.Black, board.getCellAt(Board.rowMin, Board.colMin));
			ArrayList<Cell> temp;
			temp = piece.movesInDir(board, 1, 0);
			//It must be able to move only in the same column.
			for(Cell c: temp)
				assertEquals(Board.colMin, c.col);
			assertEquals(7, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));
			
			temp = piece.movesInDir(board, -1, 0);
			//It can't go outside the board, by decrementing row from '1'.
			assertEquals(0, temp.size());		
	
			temp = piece.movesInDir(board, 0, 1);
			//It must be able to move only in the same row.
			for(Cell c: temp)
				assertEquals(Board.rowMin, c.row);
			assertEquals(7, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));
			
			temp = piece.movesInDir(board, 0, -1);
			//It can't go outside the board, by decrementing column from 'a'.
			assertEquals(0, temp.size());
		}
		catch(Exception e)
		{
			System.out.println("Exception in "
				+ "rookMovesFromCornerTest() method of PieceTest");
		}
	}

	/**
	 * Test method for concrete method
	 * piece.Piece.movesInDir(chess.Board, int, int).
	 * It should test the bishop moves along a constant difference diagonal.
	 **/
	@Test
	void bishopMovesFromCornerDiffConstantTest() 
	{
		try
		{
			//This tests moves of a bishop along a constant difference diagonal.
			//Creating a new white bishop at the cell a1.
			piece = new Bishop(Board.White, board.getCellAt(Board.rowMin, Board.colMin));
			ArrayList<Cell> temp;
			temp = piece.movesInDir(board, 1, 1);
			//It must be able to move only in the same diagonal.
			for(Cell c: temp)
				assertEquals(0, (c.col - Board.colMin)-(c.row - Board.rowMin));
			assertEquals(7, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));
			
			temp = piece.movesInDir(board, -1, -1);
			//It can't go outside the board.
			assertEquals(0, temp.size());
			
			temp = piece.movesInDir(board, 1, -1);
			//It can't go outside the board.
			assertEquals(0, temp.size());
			
			temp = piece.movesInDir(board, -1, 1);
			//It can't go outside the board.
			assertEquals(0, temp.size());
		}
		catch(Exception e)
		{
			System.out.println("Exception in "
				+ "bishopMovesFromCornerDiffConstantTest() "
				+ "method of PieceTest");
		}
	}
	
	/**
	 * Test method for concrete method
	 * piece.Piece.movesInDir(chess.Board, int, int).
	 * It should test the bishop moves along a constant sum diagonal.
	 **/
	@Test
	void bishopMovesFromCornerSumConstantTest() 
	{
		try
		{
			//This tests moves of a bishop along a constant sum diagonal.
			//Creating a new white bishop at the cell a8.
			piece = new Bishop(Board.White, board.getCellAt(Board.rowMax, Board.colMin));
			ArrayList<Cell> temp;
			
			Cell edge = board.getCellAt(Board.rowMin, Board.colMax);
			new Pawn(Board.White, edge);
			//putting an intruding white pawn on h1.
			
			temp = piece.movesInDir(board, -1, 1);
			//It must be able to move only in the same diagonal.
			for(Cell c: temp)
				assertEquals(7, (c.col - Board.colMin)+(c.row - Board.rowMin));
			assertEquals(6, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));
			assertFalse(temp.contains(edge), "A white bishop cannot attack a white pawn.");
			
			edge.setPiece(null);//destruct the old pawn.
			new Pawn(Board.Black, edge);
			//putting an intruding black pawn on h1.
			
			temp = piece.movesInDir(board, -1, 1);
			assertEquals(7, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));
			assertTrue(temp.contains(edge), "A white bishop can attack a black pawn.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in "
				+ "bishopMovesFromCornerSumConstantTest() "
				+ "method of PieceTest");
		}
	}
	
	/**
	 * Test method for {@link piece.Piece#getColour()}.
	 */
	@Test
	void getColourTest()
	{
		try
		{
			piece = new Bishop(Board.White, board.getCellAt(Board.rowMin, Board.colMin));
			//a white bishop at an edge cell is stored into a piece place-holder.
			assertEquals(Board.White, piece.getColour());
			
			piece = new Rook(Board.Black, board.getCellAt(Board.rowMax, Board.colMax));
			//a black rook at an edge cell is stored into a piece place-holder.
			assertEquals(Board.Black, piece.getColour());
		}
		catch(Exception e)
		{
			System.out.println("Exception in testGetColour() "
					+ "method of PieceTest");	
		}
	}

}
