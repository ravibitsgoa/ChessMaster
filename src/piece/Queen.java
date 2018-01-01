package piece;

import chess.Board;
import chess.Cell;

public class Queen extends Piece {
	private Board board;
	public Queen(String col, Cell cell, Board b)
	{	super(col, cell);
		this.board = b;
	}
	
	/* A queen can move only where a rook or a bishop can move.
	 * i.e. it can move in same row or column (like a rook)
	 * or it can move in same diagonal (like a bishop)
	 * I.e. it can go to dest if sum(row, column) or diff(row, column) of 
	 * its current cell and dest are same.
	 * */
	public boolean canMoveTo(Cell dest)
	{
		final char dr =dest.row, dc= dest.col;
		final char cr =currentPos.row, cc= currentPos.col;
		if(	( ((dr + dc) == (cr + cc)) || (dr-dc)== (cr-cc) ) 
			&& (dest.getPiece() == null || dest.getPiece().getColour() != this.colour)
		  )
		{	
			//If the queen is to be moved along a constant sum diagonal.
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
						//if queen is blocked by a piece of its own color, it can't move ahead.
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
						//if queen is blocked by a piece of its own color, it can't move ahead.
					}
				}
			}
			//If the queen is to be moved along a constant difference diagonal.
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
						//if queen is blocked by a piece of its own color, it can't move ahead.
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
						//if queen is blocked by a piece of its own color, it can't move ahead.
					}
				}
			}
			assert(false);
			return false;
		}
		//move like a rook.
		else if((dest.row == currentPos.row || dest.col == currentPos.col) 
				&& (dest.getPiece()==null || dest.getPiece().getColour() != this.colour))
		{	
			//If the desired cell is in the same column, check whether
			//there is any intruding piece of the same color as this queen.
			if(dest.col == currentPos.col)
			{	if(dest.row > currentPos.row)
				{	for(int i=currentPos.row+1; i<=Board.rowMax; i++)
					{	if(dest.row == i)
							return true;
						if(board.colourAt((char)i, currentPos.col) == this.colour)
						{	return false;
						}
						//if queen is blocked by a piece of its own color, it can't move ahead.
					}
				}
				else
				{	for(int i=currentPos.row-1; i>=Board.rowMin; i--)
					{	if(dest.row == i)
							return true;
						if(board.colourAt((char)i, currentPos.col) == this.colour)
						{	return false;
						}
						//if queen is blocked by a piece of its own color, it can't move ahead.
					}
				}
			}
			
			//If the desired cell is in the same row, check whether
			//there is any intruding piece of the same color as this queen.
			if(dest.row == currentPos.row)
			{	if(dest.col > currentPos.col)
				{	for(int i=currentPos.col+1; i<=Board.colMax; i++)
					{	if(dest.col == i)
							return true;
						if(board.colourAt(currentPos.row, (char)i) == this.colour)
						{	return false;
						}
						//if queen is blocked by a piece of its own color, it can't move ahead.
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
					//if queen is blocked by a piece of its own color, it can't move ahead.
				}	
			}
			
			assert(false);
			return false;
		}
		else
			return false;
	}
	
}
