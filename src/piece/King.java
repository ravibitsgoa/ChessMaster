package piece;

import chess.*;
import java.util.ArrayList;

/**
 * @author Ravishankar P. Joshi
 * */
public class King extends Piece 
{	
	public King(String col, Cell cell) throws Exception
	{	super(col, cell);
	}
	
	/**
	 * Returns the string representation of a king.
	 * @return WK for white king. BK for black king.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"K";
	}
	
	/** 
	 * A King can move only to immediately adjacent (at most 8) cells.
	 * Also, the destination cell must not be under attack, right now.
	 * (King doesn't want himself to be killed.) :P
	 * King needs to know whole board so that it can call 
	 * isUnderAttack() method of the board while moving around.
	 * */
	@Override
	public ArrayList<Cell> getAllMoves(Board board)
	{
		this.moves = new ArrayList<Cell>();	//Make the list of moves empty.
		final char cr = currentPos.row,	cc= currentPos.col;
		
		for(char row = (char)(cr-1); row<=cr+1; row++)
		{	for(char col=(char) (cc-1); col<=cc+1; col++)
			{
				Cell dest = board.getCellAt(row, col);
				if(dest == null || dest.equals(currentPos))
					continue;
			
				if( board.colourAt(dest.row, dest.col) != this.colour 
					&& !board.isUnderAttack(dest.row, dest.col, this) ) 
				{
					moves.add(dest);
				}
			}
		}
		
		return this.moves;
	}
	
}
