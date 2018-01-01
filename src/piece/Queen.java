package piece;

import chess.Cell;

public class Queen extends Piece {
	
	public Queen(String col, Cell cell)
	{	super(col, cell);
	}
	
	/* A queen can move only where a rook or a bishop can move.
	 * i.e. it can move in same row or column (like a rook)
	 * or it can move in same diagonal (like a bishop)
	 * I.e. it can go to dest if sum(row, column) or diff(row, column) of 
	 * its current cell and dest are same.
	 * */
	public boolean canMoveTo(Cell dest)
	{
		char dr =dest.row, dc= dest.col;
		char cr =currentPos.row, cc= currentPos.col;
		if(	( ((dr + dc) == (cr + cc)) || Math.abs(dr-dc)== Math.abs(cr-cc) ) 
			&& (dest.getPiece() == null || dest.getPiece().getColour() != this.colour)
		  )	//move like a bishop.
		{	
			return true;
		}
		//move like a rook.
		else if((dest.row == currentPos.row || dest.col == currentPos.col) 
			&& (dest.getPiece()==null || dest.getPiece().getColour()!=this.colour))
		{	
			return true;
		}
		else
			return false;
	}
	
}
