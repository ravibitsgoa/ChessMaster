package piece;

import chess.*;

/**
 * @author Ravishankar P. Joshi
 * */
public class Pawn extends Piece 
{	
	public final short dir;			//direction of movement of the pawn.
	//dir = 1 for a white pawn.
	//dir = -1 for a black pawn.
	public final Cell orig;			//The original cell of the pawn.
	public Pawn(String col, Cell cell)  throws Exception
	{	super(col);
		if(col.equals(Board.White))	//White pawns can move only forward.
			dir = 1;
		else						//Black pawns can move only backwards.
			dir = -1;
		this.orig = cell;
	}
	
	/**
	 * Returns the pawn as a string.
	 * @return "WP" for a white pawn. Returns "BP" otherwise.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"P";
	}
	
}
