package piece;

import chess.*;

public class King extends Piece {
	
	private Board board;
	//King needs to know whole board so that it can call 
	//isUnderAttack method of the board while moving around.
	
	public King(String col, Cell cell, Board b)
	{	super(col, cell);
		this.board = b;
	}
	
	/* A King can move only to immediately adjacent (at most 8) cells.
	 * i.e. up to Euclidean distance sqrt(2).
	 * Also, the dest cell must not be under attack, now.
	 * (King doesn't want himself to be killed :P
	 * */
	public boolean canMoveTo(Cell dest)
	{
		char dr = dest.row, 		dc= dest.col;
		char cr = currentPos.row, 	cc= currentPos.col;
		
		if( (((dr-cr)*(dr-cr) + (dc-cc)*(dc-cc)) <= 2)
			&& (dest.getPiece()==null || dest.getPiece().getColour()!=this.colour)
			&& !board.isUnderAttack(dest, this))
		{	return true;
		}
		else
			return false;
	}
	
}
