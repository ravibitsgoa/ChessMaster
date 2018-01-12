package chess;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import piece.King;
import piece.Pawn;
import piece.Rook;

/**
 * @author Ravishankar P. Joshi
 **/
class BoardTest 
{
    private Board board;

    @BeforeEach
    void setUp() 
    {
    	board = null;
    }

    @AfterEach
    void tearDown() 
    {
    	board = null;
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
				Cell temp = board.getCellAt((char)(Board.rowMin+i), 
						(char)(Board.colMin+j));
				assertNotSame(null, temp);
				assertEquals((char)(Board.rowMin+i), temp.row);
				assertEquals((char)(Board.colMin+j), temp.col);
			}
		}
    }

    /**
     * Test method for {@link chess.Board#Board(java.lang.Boolean)}.
     **/
    @Test
    void filledConstructorTest() 
    {
    	board = new Board(true);
    	for(int i=0; i<8; i++)
		{	for(int j=0; j<8; j++)
			{	
				Cell temp = board.getCellAt((char)(Board.rowMin+i), 
						(char)(Board.colMin+j));
				assertNotSame(null, temp);
				assertEquals((char)(Board.rowMin+i), temp.row);
				assertEquals((char)(Board.colMin+j), temp.col);
			}
		}
    	
    	for(int j=0; j<8; j++)
		{	
			Cell c = board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+j));	
			assertEquals(Board.White.charAt(0)+"P", c.getPiece().toString());
			//the second nearest row to white is filled with white pawns
			c = board.getCellAt((char)(Board.rowMin+6), (char)(Board.colMin+j));	
			assertEquals(Board.Black.charAt(0)+"P", c.getPiece().toString());
			//the second nearest row to black is filled with black pawns.
		}
    	
    	//testing construction of rooks:
    	{
	    	Cell rookCell = board.getCellAt(Board.rowMin, Board.colMin);	
	    	assertEquals(Board.White.charAt(0)+"R", rookCell.getPiece().toString());
	    	rookCell = board.getCellAt(Board.rowMin, Board.colMax);	
	    	assertEquals(Board.White.charAt(0)+"R", rookCell.getPiece().toString());
			
	    	rookCell = board.getCellAt(Board.rowMax, Board.colMin);	
	    	assertEquals(Board.Black.charAt(0)+"R", rookCell.getPiece().toString());
	    	rookCell = board.getCellAt(Board.rowMax, Board.colMax);	
	    	assertEquals(Board.Black.charAt(0)+"R", rookCell.getPiece().toString());
	    }
    	
    	//testing construction of knights:
    	{	
    		Cell knightCell = board.getCellAt(Board.rowMin, (char)(Board.colMin+1));	
    		assertEquals(Board.White.charAt(0)+"N", knightCell.getPiece().toString());
    		knightCell = board.getCellAt(Board.rowMin, (char)(Board.colMin+6));	
	    	assertEquals(Board.White.charAt(0)+"N", knightCell.getPiece().toString());
			
	    	knightCell = board.getCellAt((char)(Board.rowMin+7),
	    			(char)(Board.colMin+1));	
	    	assertEquals(Board.Black.charAt(0)+"N", knightCell.getPiece().toString());
	    	knightCell = board.getCellAt((char)(Board.rowMin+7), 
	    			(char)(Board.colMin+6));	
	    	assertEquals(Board.Black.charAt(0)+"N", knightCell.getPiece().toString());
		}
    	
    	//testing construction of bishops:
    	{	
    		Cell bishopCell = board.getCellAt(Board.rowMin, (char)(Board.colMin+2));	
    		assertEquals(Board.White.charAt(0)+"B", bishopCell.getPiece().toString());
    		bishopCell = board.getCellAt(Board.rowMin, (char)(Board.colMin+5));	
	    	assertEquals(Board.White.charAt(0)+"B", bishopCell.getPiece().toString());
			
	    	bishopCell = board.getCellAt((char)(Board.rowMin+7),
	    			(char)(Board.colMin+2));	
	    	assertEquals(Board.Black.charAt(0)+"B", bishopCell.getPiece().toString());
	    	bishopCell = board.getCellAt((char)(Board.rowMin+7), 
	    			(char)(Board.colMin+5));	
	    	assertEquals(Board.Black.charAt(0)+"B", bishopCell.getPiece().toString());
		}
    	
    	//testing construction of queens:
    	Cell queenCell = board.getCellAt(Board.rowMin, (char)(Board.colMin+4));	
		assertEquals(Board.White.charAt(0)+"Q", queenCell.getPiece().toString());
		queenCell = board.getCellAt((char)(Board.rowMin+7), (char)(Board.colMin+4));	
    	assertEquals(Board.Black.charAt(0)+"Q", queenCell.getPiece().toString());
    	
    	//testing construction of queens:
        Cell kingCell = board.getCellAt(Board.rowMin, (char)(Board.colMin+3));	
		assertEquals(Board.White.charAt(0)+"K", kingCell.getPiece().toString());
		kingCell = board.getCellAt((char)(Board.rowMin+7), (char)(Board.colMin+3));	
    	assertEquals(Board.Black.charAt(0)+"K", kingCell.getPiece().toString());
	
    }

    /**
     * Test method for {@link chess.Board#getCellAt(char, char)}.
     **/
    @Test
    void getCellAtTest() 
    {
    	board = new Board();
    	Cell a1= board.getCellAt(Board.rowMin, Board.colMin);
    	assertEquals(Board.rowMin, a1.row);
    	assertEquals(Board.colMin, a1.col);
    	
    	Cell invalid= board.getCellAt((char)(Board.rowMin-1), Board.colMin);
    	assertEquals(null, invalid);
    	invalid= board.getCellAt((char)(Board.rowMax+1), Board.colMin);
    	assertEquals(null, invalid);
    	invalid= board.getCellAt(Board.rowMin, (char)(Board.colMin-1));
    	assertEquals(null, invalid);
    	invalid= board.getCellAt(Board.rowMin, (char)(Board.colMax+1));
    	assertEquals(null, invalid);
    	
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
	     	Cell a1= board.getCellAt(Board.rowMin, Board.colMin);
	     	Rook rook = new Rook(Board.White, a1);
	     	Cell b2= board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+1));
	     	
	     	King blackKing = new King(Board.Black, b2);
	     	assertFalse(board.isUnderAttack(a1.row, a1.col, blackKing),
	     			"cell a1 is not under attack by anyone other than black king.");
	     	assertFalse(board.isUnderAttack(b2.row, b2.col, blackKing),
	     			"cell b2 is not under attack.");
	     	
	     	Cell b1= board.getCellAt(Board.rowMin, (char)(Board.colMin+1));
	     	Cell a2= board.getCellAt((char)(Board.rowMin+1), Board.colMin);
	     	Cell a3= board.getCellAt((char)(Board.rowMin+2), Board.colMin);
	     	assertTrue(board.isUnderAttack(a2.row, a2.col, blackKing),
	     			"cell a2 is under attack by the rook.");
	     	assertFalse(board.isUnderAttack(a2.row, a2.col, rook),
	     			"cell a2 is under attack only by the rook.");
	     	assertTrue(board.isUnderAttack(b1.row, b1.col, blackKing),
	      			"cell a2 is under attack by the rook.");
	 	    assertTrue(board.isUnderAttack(a3.row, a3.col, blackKing),
	 	  			"cell a2 is under attack by the rook.");
	 	    
	 	    assertFalse(board.isUnderAttack((char)(Board.rowMin-1), 
	 	    		Board.colMin, blackKing));
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
    		String invalid = board.colourAt((char)(Board.rowMin-1), Board.colMin);
    		assertEquals(null, invalid);
    		
    		assertEquals(null, board.colourAt(Board.rowMin, Board.colMin),
    				"colourAt() should return null for an empty cell.");
    		
    		Cell rookCell = board.getCellAt(Board.rowMin, (char)(Board.colMin+2));
    		new Rook(Board.White, rookCell);
    		//Created a new white rook on cell c1.
    		assertEquals(Board.White, board.colourAt(rookCell),
    				"colourAt() method should return white for a cell "+
    				"containing a white rook.");
    		
    		rookCell = board.getCellAt(Board.rowMin, (char)(Board.colMin+4));
    		new Rook(Board.Black, rookCell);
    		//Created a new black rook on cell e1.
    		assertEquals(Board.Black, board.colourAt(rookCell),
    				"colourAt() method should return black for a cell "+
    				"containing a black rook.");
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception in colourAtTest() of BoardTest");
    	}
    }

}
