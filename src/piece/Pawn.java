package piece;

import chess.Cell;

public class Pawn extends Piece {
	private short dir;
	public Pawn(String col, Cell cell)
	{	super(col, cell);
		if(col == "W")	//White pawns can move only forward.
			dir = 1;
		else			//Black pawns can move only backwards.
			dir = -1;
	}
	
	/* A pawn can move only to a direction dir fixed by its color, 
	 * it can go to immediate next row (in that direction), and 
	 * move straight or one step diagonally to kill a piece.
	 * */
	public boolean moveTo(Cell dest)
	{	
		if(  (dest.row == currentPos.row + this.dir) && 
			((dest.col == currentPos.col && dest.getPiece()==null)
			|| ((dest.col == currentPos.col-1 || dest.col == currentPos.col+1)
			 && dest.getPiece()!=null && dest.getPiece().getColour()!=this.colour))
		  )
		{	currentPos.setPiece(null);
			dest.setPiece(this);
			currentPos = dest;
			return true;
		}
		else
			return false;
	}
	
}
