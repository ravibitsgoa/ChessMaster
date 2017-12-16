package piece;
import chess.*;

public class Rook extends Piece 
{
	public Rook(String col, chess.Cell cell)
	{	this.colour = col;
		this.currentPos = cell;
	}
	
	public boolean moveTo(Cell dest)
	{
		if(dest.row != currentPos.row || dest.col!= currentPos.col || dest.getPiece() != null)
			return false;
		else
		{	currentPos.setPiece(null);
			dest.setPiece(this);
			currentPos = dest;
			return true;
		}
	}
}
