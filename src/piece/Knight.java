package piece;

import chess.*;

public class Knight extends Piece {
	
	public Knight(String col, Cell cell)
	{	super(col, cell);
	}
	
	public String toString()
	{
		return colour.charAt(0)+"N";
	}
	
	/* A knight can move only 2 in one dimension and 1 in the other.
	 * i.e. it can move only by 5 in squared Euclidean distance.
	 * */
	public boolean canMoveTo(Cell dest, Board board)
	{
		char dr = dest.row, 		dc= dest.col;
		char cr = currentPos.row, 	cc= currentPos.col;
		
		if( (((dr-cr)*(dr-cr) + (dc-cc)*(dc-cc)) == 5)
			&& (dest.getPiece()==null || dest.getPiece().getColour()!=this.colour))
		{	return true;
		}
		else
			return false;
	}
	
}
