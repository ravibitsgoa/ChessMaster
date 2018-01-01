package piece;

import chess.*;

public class Bishop extends Piece {
	
	public Bishop(String col, Cell cell)
	{	super(col, cell);
	}
	
	public String toString()
	{
		return colour.charAt(0)+"B";
	}
	
	/* A bishop can move only diagonally, hence
	 * sum or difference of (row, col) of current and 
	 * destination cell must be the same.
	 */
	public boolean canMoveTo(Cell dest, Board board)
	{
		final char dr =dest.row, dc= dest.col;
		final char cr =currentPos.row, cc= currentPos.col;
		if(	( ((dr + dc) == (cr + cc)) || (dr-dc)== (cr-cc) ) 
			&& (dest.getPiece() == null || dest.getPiece().getColour() != this.colour)
		  )
		{	
			//If the bishop is to be moved along a constant sum diagonal.
			if((dc+dr) == (cr+cc))
			{	final int sum = dr+dc;
				if(dest.row > currentPos.row)
				{	for(int i=currentPos.row+1; 
						i<=Board.rowMax && i<=(sum-Board.colMin); i++)
					{	if(dest.row == i)
							return true;
						//sum-i will be column character/number.
						if(board.colourAt((char)i, (char)(sum-i)) == this.colour)
						{	return false;
						}
						//if bishop is blocked by a piece of its own color, it can't move ahead.
						
						if(board.colourAt((char)i, (char)(sum-i)) != null)
						{	return false;
						}
						//if bishop is blocked by a piece of opposite color, it can't move ahead.
					}
				}
				else
				{
					for(int i=currentPos.row-1; 
							i>=Board.rowMin && i>=(sum-Board.colMax); i--)
					{	if(dest.row == i)
							return true;
						//sum-i will be column character/number.
						if(board.colourAt((char)i, (char)(sum-i)) == this.colour)
						{	return false;
						}
						//if bishop is blocked by a piece of its own color, it can't move ahead.
					
						if(board.colourAt((char)i, (char)(sum-i)) != null)
						{	return false;
						}
						//if bishop is blocked by a piece of opposite color, it can't move ahead.
					}
				}
			}
			//If the bishop is to be moved along a constant difference diagonal.
			else
			{	final int diff = dr-dc;
				if(dest.row > currentPos.row)
				{	for(int i=currentPos.row+1; 
						i<=Board.rowMax && i<=(diff-Board.colMax); i++)
					{	if(dest.row == i)
							return true;
						//i-(dr-dc) will be column character/number.
						if(board.colourAt((char)i, (char)(i-diff)) == this.colour)
						{	return false;
						}
						//if bishop is blocked by a piece of its own color, it can't move ahead.
					
						if(board.colourAt((char)i, (char)(i-diff)) != null)
						{	return false;
						}
						//if bishop is blocked by a piece of opposite color, it can't move ahead.
					}
				}
				else
				{
					for(int i=currentPos.row-1; 
							i>=Board.rowMin && i>=(diff-Board.colMin); i--)
					{	if(dest.row == i)
							return true;
						//i-(dr-dc) will be column character/number.
						if(board.colourAt((char)i, (char)(i-diff)) == this.colour)
						{	return false;
						}
						//if bishop is blocked by a piece of its own color, it can't move ahead.
					
						if(board.colourAt((char)i, (char)(i-diff)) != null)
						{	return false;
						}
						//if bishop is blocked by a piece of opposite color, it can't move ahead.
					}
				}
			}
			assert(false);
			return false;
		}
		else
			return false;
	}
	
}
