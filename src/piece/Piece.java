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
	 * and returns true.
	 * returns false otherwise, without any modification to anything.
	 * */
	protected abstract boolean moveTo(Cell dest);
	protected String getColour() 
	{
		return colour;
	}
	
}
