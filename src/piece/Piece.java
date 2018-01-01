package piece;
import chess.Cell;

public abstract class Piece 
{
	protected String colour;
	protected Cell currentPos;

	/* Constructor of this abstract class to avoid repetition 
	 * in child classes, whose constructor does mainly this.
	 */
	public Piece(String col, Cell cell)
	{	this.colour = col;
		this.currentPos = cell;
	}
	
	/* Checks whether the Piece can be moved into cell dest or not.
	 * If it can be moved to dest, it moves itself, changes occupant Piece of the cells,
	 * and returns true. (It calls canMoveTo method to decide this.)
	 * returns false otherwise, without any modifying anything.
	 * */
	protected boolean moveTo(Cell dest)
	{
		if(canMoveTo(dest))
		{	currentPos.setPiece(null);
			dest.setPiece(this);
			currentPos = dest;
			return true;
		}
		else
			return false;
	}
	
	/* This method returns true if this piece can move to cell dest,
	 * returns false otherwise.
	 * This method doesn't modify anything.
	 * */
	public abstract boolean canMoveTo(Cell dest);
	
	public String getColour() 
	{
		return colour;
	}
	
}
