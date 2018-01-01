package piece;
import chess.*;

public class Rook extends Piece 
{
	private Board board;
	public Rook(String col, Cell cell, Board b)
	{	super(col, cell);
		this.board = b;
	}
	
	/* A rook can move only in same row/ column.
	 * A piece can move to dest cell only if 
	 * 1. it's either empty or
	 * 2. occupied by a piece of opposite color.*/
	public boolean canMoveTo(Cell dest)
	{
		
		if((dest.row == currentPos.row || dest.col == currentPos.col) 
			&& (dest.getPiece()==null || dest.getPiece().getColour() != this.colour))
		{	
			//If the desired cell is in the same column, check whether
			//there is any intruding piece of the same color as this rook.
			if(dest.col == currentPos.col)
			{	if(dest.row > currentPos.row)
				{	for(int i=currentPos.row+1; i<=Board.rowMax; i++)
					{	if(dest.row == i)
							return true;
						if(board.colourAt((char)i, currentPos.col) == this.colour)
						{	return false;
						}
						//if rook is blocked by a piece of its own color, it can't move ahead.
					}
				}
				else
				{	for(int i=currentPos.row-1; i>=Board.rowMin; i--)
					{	if(dest.row == i)
							return true;
						if(board.colourAt((char)i, currentPos.col) == this.colour)
						{	return false;
						}
						//if rook is blocked by a piece of its own color, it can't move ahead.
					}
				}
			}
			
			//If the desired cell is in the same row, check whether
			//there is any intruding piece of the same color as this rook.
			if(dest.row == currentPos.row)
			{	if(dest.col > currentPos.col)
				{	for(int i=currentPos.col+1; i<=Board.colMax; i++)
					{	if(dest.col == i)
							return true;
						if(board.colourAt(currentPos.row, (char)i) == this.colour)
						{	return false;
						}
						//if rook is blocked by a piece of its own color, it can't move ahead.
					}
				}
				else
				{	for(int i=currentPos.col-1; i>=Board.colMin; i--)
					{	if(dest.col == i)
							return true;
						if(board.colourAt(currentPos.row, (char)i) == this.colour)
						{	return false;
						}
					}
					//if rook is blocked by a piece of its own color, it can't move ahead.
				}	
			}
			
			assert(false);
			return false;
		}
		else
			return false;
	}
	
}
