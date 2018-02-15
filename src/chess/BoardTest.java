package chess;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import piece.King;
import piece.Rook;

/**
 * @author Ravishankar P. Joshi
 **/
class BoardTest 
{
    private Board board;
    private Movement movement;

    @BeforeEach
    void setUp() 
    {
    	board = null;
    	movement = null;
    }

    @AfterEach
    void tearDown() 
    {
    	board = null;
    	movement = null;
    }

    /**
     * Test method for {@link chess.Board#Board()}.
     **/
    @Test
    void defaultConstructorTest() 
    {
    	board = new Board();
    	for(int i=0; i<8; i++)
		{	for(int j=0; j<8; j++)
			{	
				Cell temp = board.getCellAt(Board.rowMin+i, Board.colMin+j);
				assertNotNull(temp);
				assertEquals(Board.rowMin+i, temp.row);
				assertEquals(Board.colMin+j, temp.col);
			}
		}
    }

    /**
     * Checks whether Board's opposite colour test method uses by reference
     * equality.
     * 
     * If so, the test fails. 
     * Otherwise, if the Board uses .equals method, and returns black for white
     * and vice-versa, the test passes.
     * */
    @Test
    void oppositeColourTest()
    {
    	String whiteColour = new String(Board.White);
    	String blackColour = new String(Board.Black);
    	
    	assertEquals(blackColour, Board.opposite(whiteColour));
    	assertEquals(whiteColour, Board.opposite(blackColour));
    	assertEquals(whiteColour, Board.opposite(Board.opposite(whiteColour)));
    }
    
    /**
     * Test method for {@link chess.Board#Board(java.lang.Boolean)}.
     **/
    @Test
    void filledConstructorTest() 
    {
    	board = new Board(true);
		movement = board.getMovement();
    	for(int i=0; i<8; i++)
		{	for(int j=0; j<8; j++)
			{	
				Cell temp = board.getCellAt(Board.rowMin+i, Board.colMin+j);
				assertNotNull(temp);
				assertEquals(Board.rowMin+i, temp.row);
				assertEquals(Board.colMin+j, temp.col);
			}
		}
    	
    	for(int j=0; j<8; j++)
		{	
			Cell c = board.getCellAt(Board.rowMin+1, Board.colMin+j);	
			assertEquals(Board.White.charAt(0)+"P", 
					movement.getPieceOn(c).toString());
			//the second nearest row to white is filled with white pawns
			c = board.getCellAt(Board.rowMin+6, Board.colMin+j);	
			assertEquals(Board.Black.charAt(0)+"P", 
					movement.getPieceOn(c).toString());
			//the second nearest row to black is filled with black pawns.
		}
    	
    	//testing construction of rooks:
    	{
	    	Cell rookCell = board.getCellAt(Board.rowMin, Board.colMin);	
	    	assertEquals(Board.White.charAt(0)+"R", 
	    			movement.getPieceOn(rookCell).toString());
	    	rookCell = board.getCellAt(Board.rowMin, Board.colMax);	
	    	assertEquals(Board.White.charAt(0)+"R", 
	    			movement.getPieceOn(rookCell).toString());
			
	    	rookCell = board.getCellAt(Board.rowMax, Board.colMin);	
	    	assertEquals(Board.Black.charAt(0)+"R", 
	    			movement.getPieceOn(rookCell).toString());
	    	rookCell = board.getCellAt(Board.rowMax, Board.colMax);	
	    	assertEquals(Board.Black.charAt(0)+"R", 
	    			movement.getPieceOn(rookCell).toString());
	    }
    	
    	//testing construction of knights:
    	{	
    		Cell knightCell = board.getCellAt(Board.rowMin, Board.colMin+1);	
    		assertEquals(Board.White.charAt(0)+"N", 
    				movement.getPieceOn(knightCell).toString());
    		knightCell = board.getCellAt(Board.rowMin, Board.colMin+6);	
	    	assertEquals(Board.White.charAt(0)+"N", 
	    			movement.getPieceOn(knightCell).toString());
    		
	    	knightCell = board.getCellAt(Board.rowMin+7, Board.colMin+1);	
	    	assertEquals(Board.Black.charAt(0)+"N", 
	    			movement.getPieceOn(knightCell).toString());
    		knightCell = board.getCellAt(Board.rowMin+7, Board.colMin+6);	
	    	assertEquals(Board.Black.charAt(0)+"N", 
	    			movement.getPieceOn(knightCell).toString());
    	}
    	
    	//testing construction of bishops:
    	{	
    		Cell bishopCell = board.getCellAt(Board.rowMin, Board.colMin+2);	
    		assertEquals(Board.White.charAt(0)+"B", 
    				movement.getPieceOn(bishopCell).toString());
    		bishopCell = board.getCellAt(Board.rowMin, Board.colMin+5);	
	    	assertEquals(Board.White.charAt(0)+"B", 
    				movement.getPieceOn(bishopCell).toString());

	    	bishopCell = board.getCellAt(Board.rowMin+7, Board.colMin+2);	
	    	assertEquals(Board.Black.charAt(0)+"B", 
    				movement.getPieceOn(bishopCell).toString());
	    	bishopCell = board.getCellAt(Board.rowMin+7, Board.colMin+5);	
	    	assertEquals(Board.Black.charAt(0)+"B", 
    				movement.getPieceOn(bishopCell).toString());
    	}
    	
    	//testing construction of queens:
    	Cell queenCell = board.getCellAt(Board.rowMin, Board.colMin+3);	
		assertEquals(Board.White.charAt(0)+"Q", 
				movement.getPieceOn(queenCell).toString());
		queenCell = board.getCellAt(Board.rowMax, Board.colMin+3);	
    	assertEquals(Board.Black.charAt(0)+"Q", 
    			movement.getPieceOn(queenCell).toString());
		 	
    	//testing construction of queens:
        Cell kingCell = board.getCellAt(Board.rowMin, Board.colMin+4);	
		assertEquals(Board.White.charAt(0)+"K", 
				movement.getPieceOn(kingCell).toString());
		kingCell = board.getCellAt(Board.rowMax, Board.colMin+4);	
    	assertEquals(Board.Black.charAt(0)+"K", 
    			movement.getPieceOn(kingCell).toString());
	
    }

    /**
     * Test method for {@link chess.Board#getCellAt(char, char)}.
     **/
    @Test
    void getCellAtTest() 
    {
    	board = new Board();
		movement = board.getMovement();
    	Cell a1= board.getCellAt(Board.rowMin, Board.colMin);
    	assertEquals(Board.rowMin, a1.row);
    	assertEquals(Board.colMin, a1.col);
    	
    	Cell invalid= board.getCellAt(Board.rowMin-1, Board.colMin);
    	assertNull(invalid);
    	invalid= board.getCellAt(Board.rowMax+1, Board.colMin);
    	assertNull(invalid);
    	invalid= board.getCellAt(Board.rowMin, Board.colMin-1);
    	assertNull(invalid);
    	invalid= board.getCellAt(Board.rowMin, Board.colMax+1);
    	assertNull(invalid);
    	
    }

    /**
     * Test method for {@link chess.Board#isUnderAttack(char, char, piece.Piece)}.
     **/
    @Test
    void isUnderAttackTest() 
    {
    	try 
    	{
	     	board = new Board();
    		movement = board.getMovement();
	     	Cell a1= board.getCellAt(Board.rowMin, Board.colMin);
	     	Rook rook = new Rook(Board.White);
	     	board.construct(rook, a1);
	     	
	     	Cell b2= board.getCellAt(Board.rowMin+1, Board.colMin+1);
	     	King blackKing = new King(Board.Black);
	     	board.construct(blackKing, b2);
	     	assertFalse(movement.isUnderAttack(a1, Board.Black),
	     			"cell a1 is not under attack by any white piece.");
	     	assertFalse(movement.isUnderAttack(b2, Board.Black),
	     			"cell b2 is not under attack by any white piece.");
	     	//board.print();
	     	Cell b1= board.getCellAt(Board.rowMin, Board.colMin+1);
	     	Cell a2= board.getCellAt(Board.rowMin+1, Board.colMin);
	     	Cell a3= board.getCellAt(Board.rowMin+2, Board.colMin);
	     	assertTrue(movement.isUnderAttack(a2, Board.Black),
	     			"cell a2 is under attack by the white rook.");
	     	assertTrue(movement.isUnderAttack(b1, Board.Black),
	      			"cell a2 is under attack by the white rook.");
	 	    assertTrue(movement.isUnderAttack(a3, Board.Black),
	 	  			"cell a2 is under attack by the white rook.");
	 	    
	 	    assertFalse(movement.isUnderAttack(
	 	    		board.getCellAt(Board.rowMin-1,	Board.colMin), 
	 	    		Board.Black));
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception in isUnderAttackTest() of BoardTest"); 
    	}
    }

    /**
     * Test method for {@link chess.Board#colourAt(char, char)}.
     **/
    @Test
    void colourAtTest() 
    {
    	try
    	{
    		board = new Board();
    		movement = board.getMovement();
    		String invalid = movement.colourAt(
    				(char)(Board.rowMin-1), Board.colMin);
    		assertNull(invalid);
    		
    		assertNull(movement.colourAt(Board.rowMin, Board.colMin),
    				"colourAt() should return null for an empty cell.");
    		
    		Cell rookCell = board.getCellAt(Board.rowMin, Board.colMin+2);
    		board.construct(new Rook(Board.White), rookCell);
    		//Created a new white rook on cell c1.
    		assertEquals(Board.White, movement.colourAt(rookCell),
    				"colourAt() method should return white for a cell "+
    				"containing a white rook.");
    		
    		rookCell = board.getCellAt(Board.rowMin, Board.colMin+4);
    		board.construct(new Rook(Board.Black), rookCell);
    		//Created a new black rook on cell e1.
    		assertEquals(Board.Black, movement.colourAt(rookCell),
    				"colourAt() method should return black for a cell "+
    				"containing a black rook.");
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception in colourAtTest() of BoardTest");
    	}
    }

}
