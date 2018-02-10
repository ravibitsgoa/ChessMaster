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
	private Movement movement;
	@BeforeEach
	void setUp()
	{
		piece = null;
		board = new Board();
		movement = board.getMovement();
	}

	@AfterEach
	void tearDown()
	{
		piece = null;
		board = null;
		movement = null;
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
			piece = new Rook(Board.Black);
			movement.construct(piece, a1);
			
			assertEquals(Board.Black, piece.colour);
			
			assertEquals(piece, movement.getPieceOn(a1));
		}
		catch(Exception e)
		{
			System.out.println("Exception in method constructorTest() "
					+ "of PieceTest");
		}
		
		Executable closureContainingCodeToTest = () -> piece = 
				new Rook(null);
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
			piece = new Rook(Board.Black);
			movement.construct(piece, board.getCellAt(Board.rowMin, Board.colMin));
			
			Cell dest= board.getCellAt(Board.rowMin+2, Board.colMin);
			
			assertNull(movement.moveTo(piece, null),
				"Movement object must return null when destination is null.");
			//board.print();
			//System.out.println(dest);
			assertNotNull(movement.moveTo(piece, dest), 
				"rook must be able to move to cell a3 from a1.");
			
			assertNull(movement.colourAt(Board.rowMin, Board.colMin), 
				"a1 cell must be empty now.");
			
			assertEquals(piece, movement.getPieceOn(dest), 
				"rook must be on the dest cell now.");
			//System.out.println("hmm");
			//board.print();
			ArrayList<Cell> movesList = movement.getAllMoves(piece);
			//System.out.println("test"+movesList);
			assertEquals(14, movesList.size(), 
				"rook must have 14 moves from cell a3.");
			
			//board.print();
			dest= board.getCellAt(Board.rowMin+1, Board.colMin+1);
			assertNull(movement.moveTo(piece, dest), 
				"rook must not be able to move to b2 from a3.");
			//System.out.println(movement.getAllMoves(piece));
			assertEquals(movesList, movement.getAllMoves(piece), 
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
			piece = new Rook(Board.Black);
			movement.construct(piece, board.getCellAt(Board.rowMin, Board.colMin));
			
			Cell dest = board.getCellAt(Board.rowMax, Board.colMin);
			assertFalse(movement.canMoveTo(piece, null),
					"A piece must return false when destination is null.");
			
			Boolean x = movement.canMoveTo(piece, dest);
			assertEquals(14, movement.getAllMoves(piece).size(),
					"A rook can have exactly 14 moves from a1 cell.");
			assertEquals(true, x, "A rook must be able to move"
					+ " from a1 to a8.");	
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
			piece = new Rook(Board.White);
			movement.construct(piece, board.getCellAt(Board.rowMin, Board.colMin));
			ArrayList<Cell> moves = movement.getAllMoves(piece);
			//Make sure that canMoveTo() doesn't mess with
			//already filled list of moves.
			assertEquals(14, movement.getAllMoves(piece).size());
			assertEquals(moves, movement.getAllMoves(piece));
			Boolean y = movement.canMoveTo( piece,
					board.getCellAt(Board.rowMax, Board.colMin));
			assertEquals(true, y);
			Boolean selfCell = movement.canMoveTo( piece,
					board.getCellAt(Board.rowMin, Board.colMin));
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
			piece = new Rook(Board.Black);
			Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
			movement.construct(piece, a1);
			ArrayList<Cell> moves;
			moves = movement.getAllMoves(piece);
			
			assertEquals(14, moves.size(), "Rook can have exactly 14 moves"
					+ " from a1 cell.");
			for(Cell c: moves)
			{	
				assertTrue(Board.colMin == c.col || Board.rowMin==c.row,
					"Rook must be able to move only in the same column/row.");
				assertTrue(c.row>=Board.rowMin && c.col>=Board.colMin, 
					"Rook can't go outside the board");
				assertTrue(c.row<=Board.rowMax && c.col<=Board.colMax,
					"Rook can't go outside the board");
			}
			assertFalse(moves.contains(a1));
			
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
			piece = new Bishop(Board.White);
			Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
			movement.construct(piece, a1);
			ArrayList<Cell> moves;
			moves = movement.getAllMoves(piece);
			assertEquals(7, moves.size(), "A bishop can have exactly 7 moves"
					+ " from cell a1.");
			for(Cell c: moves)
			{	
				assertEquals((c.col - Board.colMin),
							 (c.row - Board.rowMin),
				"Bishop must be able to move only in the same diagonal.");

				assertTrue(c.row >= Board.rowMin && c.col >= Board.colMin, 
						"A piece can't go outside the board");
				assertTrue(c.row <= Board.rowMax && c.col <= Board.colMax,
						"A piece can't go outside the board");			
			}
			assertFalse(moves.contains(a1));
			
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
			piece = new Bishop(Board.White);
			movement.construct(piece, board.getCellAt(Board.rowMax, Board.colMin));
			
			Cell h1 = board.getCellAt(Board.rowMin, Board.colMax);
			Pawn whitePawn = new Pawn(Board.White, h1);
			movement.construct(whitePawn, h1);
			//putting an intruding white pawn on h1.
			
			ArrayList<Cell> moves = movement.getAllMoves(piece);
			//It must be able to move only in the same diagonal.
			for(Cell c: moves)
			{	
				assertEquals(7, 
						(c.col - Board.colMin)+(c.row - Board.rowMin));
			}
			assertEquals(6, moves.size());
			Cell a1 = board.getCellAt(Board.rowMin, Board.colMin);
			assertFalse(moves.contains(a1));
			assertFalse(moves.contains(h1), 
					"A white bishop cannot attack a white pawn.");
			
			board.kill(whitePawn);
			//destruct the old pawn.
			movement.construct(new Pawn(Board.Black, h1), h1);
			//putting an intruding black pawn on h1.
			
			moves = movement.getAllMoves(piece);
			assertEquals(7, moves.size());
			assertFalse(moves.contains(a1));
			assertTrue(moves.contains(h1), 
					"A white bishop can attack a black pawn.");
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
			piece = new Bishop(Board.White);
			//a white bishop is stored into a piece place-holder.
			assertEquals(Board.White, piece.getColour());
			
			piece = new Rook(Board.Black);
			//a black rook is stored into a piece place-holder.
			assertEquals(Board.Black, piece.getColour());
			
			piece = new Knight(Board.Black);
			//a black knight is stored into a piece place-holder.
			assertEquals(Board.Black, piece.getColour());
			
			piece = new King(Board.White);
			//a white king is stored into a piece place-holder.
			assertEquals(Board.White, piece.getColour());
			
			piece = new Queen(Board.Black);
			//a black queen is stored into a piece place-holder.
			assertEquals(Board.Black, piece.getColour());
			
			piece = new Pawn(Board.White, 
					board.getCellAt(Board.rowMin, Board.colMin));
			//a white pawn at cell 'a1' is stored into a piece place-holder.
			assertEquals(Board.White, piece.getColour());
		}
		catch(Exception e)
		{
			System.out.println("Exception in testGetColour() "
					+ "method of PieceTest");	
		}
	}

}
