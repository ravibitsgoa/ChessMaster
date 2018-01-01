package piece;

import chess.Cell;

public class King extends Piece {
	
	public King(String col, Cell cell)
	{	super(col, cell);
	}
	
	/* A King can move only to immediately adjacent (at most 8) cells.
	 * i.e. up to Euclidean distance sqrt(2).
	 * Also, the dest cell must not be under attack, now.
	 * (King doesn't want himself to be killed :P
	 * */
	public boolean moveTo(Cell dest)
	{
		char dr = dest.row, 		dc= dest.col;
		char cr = currentPos.row, 	cc= currentPos.col;
		
		if( (((dr-cr)*(dr-cr) + (dc-cc)*(dc-cc)) <= 2)
			&& (dest.getPiece()==null || dest.getPiece().getColour()!=this.colour)
			&& notUnderAttack(dest))
		{	currentPos.setPiece(null);
			dest.setPiece(this);
			currentPos = dest;
			return true;
		}
		else
			return false;
	}
	
}
