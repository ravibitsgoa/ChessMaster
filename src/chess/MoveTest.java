/**
 * Unit tests for Move class.
 **/
package chess;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import piece.*;

/**
 * @author Ravishankar P. Joshi
 **/
class MoveTest 
{
	Move move;
	@BeforeEach
	void setUp()
	{	
		move = null;
	}

	@AfterEach
	void tearDown()
	{
		move = null;
	}

	/**
	 * Test method for {@link chess.Move#getSource()}.
	 */
	@Test
	void getSourceTest() 
	{
		try 
		{
			Rook rook = new Rook(Board.White), empty = null;
			Cell a1 = new Cell(Board.rowMin, Board.colMin);
			Cell a8 = new Cell(Board.rowMax, Board.colMin);
			move = new Move(a1, a8, rook, empty, Movement.normalMove);
			assertEquals(a1, move.getSource());
			assertEquals(Movement.normalMove, move.moveType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in getSourceTest() of MoveTest");
		}
	}

	/**
	 * Test method for {@link chess.Move#getDestination()}.
	 */
	@Test
	void getDestinationTest() 
	{
		try 
		{
			Rook rook = new Rook(Board.White), empty = null;
			Cell a1 = new Cell(Board.rowMin, Board.colMin);
			Cell a8 = new Cell(Board.rowMax, Board.colMin);
			move = new Move(a1, a8, rook, empty, Movement.normalMove);
			assertEquals(a8, move.getDestination());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in getDestinationTest() of"
					+ " MoveTest");
		}
	}
	
	/**
	 * Test method for {@link chess.Move#getSourcePiece()}.
	 */
	@Test
	void getSourcePieceTest() 
	{
		try 
		{
			Rook rook = new Rook(Board.White), empty = null;
			Cell a1 = new Cell(Board.rowMin, Board.colMin);
			Cell a8 = new Cell(Board.rowMax, Board.colMin);
			move = new Move(a1, a8, rook, empty, Movement.normalMove);
			assertEquals(rook, move.getSourcePiece());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in getSourcePieceTest() of"
					+ " MoveTest");
		}
	}

	/**
	 * Test method for {@link chess.Move#getDestinationPiece()}.
	 */
	@Test
	void getDestinationPieceTest() 
	{
		try 
		{
			Rook rook = new Rook(Board.White), empty = null;
			Cell a1 = new Cell(Board.rowMin, Board.colMin);
			Cell a8 = new Cell(Board.rowMax, Board.colMin);
			move = new Move(a1, a8, rook, empty, Movement.normalMove);
			assertNull(move.getDestinationPiece());
			
			Rook blackRook = new Rook(Board.Black);
			Cell h8 = new Cell(Board.rowMax, Board.colMax);
			move = new Move(h8, a8, blackRook, rook, Movement.normalMove);
			assertEquals(rook, move.getDestinationPiece());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in getDestinationPieceTest() of"
					+ " MoveTest");
		}
	}

	/**
	 * Test method for {@link chess.Move#toString()}.
	 */
	@Test
	void toStringTest() 
	{
		try 
		{
			Rook rook = new Rook(Board.White), empty = null;
			Cell a1 = new Cell(Board.rowMin, Board.colMin);
			Cell a8 = new Cell(Board.rowMax, Board.colMin);
			move = new Move(a1, a8, rook, empty, Movement.normalMove);
			assertEquals("Ra8", move.toString());
			
			Rook blackRook = new Rook(Board.Black);
			Cell h8 = new Cell(Board.rowMax, Board.colMax);
			move = new Move(h8, a8, blackRook, rook, Movement.normalMove);
			assertEquals("Rxa8", move.toString());
			
			Cell a7 = new Cell((char)(Board.rowMax-1), Board.colMin);
			Cell a6 = new Cell((char)(Board.rowMax-2), Board.colMin);
			Pawn pawn = new Pawn(Board.Black, a7);
			move = new Move(a7, a6, pawn, empty, Movement.normalMove);
			assertEquals("a6", move.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in toStringTest() of"
					+ " MoveTest");
		}
	}

}
