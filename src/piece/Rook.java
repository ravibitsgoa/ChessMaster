package piece;
import chess.*;

public class Rook extends Piece 
{
	public Rook(String col, Cell cell)
	{	super(col, cell);
	}
	
	/* A rook can move only in same row/ column.
	 * A piece can move to dest cell only if 
	 * 1. it's either empty or
	 * 2. occupied by a piece of opposite color.*/
	public boolean moveTo(Cell dest)
	{
		
		if((dest.row == currentPos.row || dest.col== currentPos.col) 
			&& (dest.getPiece()==null || dest.getPiece().getColour()!=this.colour))
		{	currentPos.setPiece(null);
			dest.setPiece(this);
			currentPos = dest;
			return true;
		}
		else
			return false;
	}
	
}
