package piece;

import chess.Cell;

public class Bishop extends Piece {
	
	public Bishop(String col, Cell cell)
	{	super(col, cell);
	}
	
	/* A bishop can move only diagonally, hence
	 * sum or difference of (row, col) of current and 
	 * destination cell must be the same.
	 */
	public boolean canMoveTo(Cell dest)
	{
		char dr =dest.row, dc= dest.col;
		char cr =currentPos.row, cc= currentPos.col;
		if(	( ((dr + dc) == (cr + cc)) || Math.abs(dr-dc)== Math.abs(cr-cc) ) 
			&& (dest.getPiece() == null || dest.getPiece().getColour() != this.colour)
		  )
		{	
			return true;
		}
		else
			return false;
	}
	
}
