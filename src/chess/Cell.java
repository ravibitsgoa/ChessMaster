package chess;
import piece.*;

public class Cell 
{
	private Piece piece;
	public final char row;
	public final char col;
	
	public Cell(char r, char c)
	{
		row = r;
		col = c;
	}
	
	public boolean setPiece(Piece newPiece)
	{
		this.piece = newPiece;
		return true;
	}
	
	public Piece getPiece()
	{
		return this.piece;
	}
	
	public String toString()
	{
		return this.col+""+this.row;
	}
	
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Cell))
			return false;
		Cell c = (Cell) obj;
		if(c.col== this.col && c.row == this.row)
			return true;
		else
			return false;
	}
}
