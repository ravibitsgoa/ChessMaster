package chess;

/**
 * @author Ravishankar P. Joshi
 * */
public class Cell
{
	public final char row;
	public final char col;
	private boolean selected;
	private boolean nextMove;
	
	/** Throws an exception if given row and column character
	 * are outside bounds(1-8, a-h) specified in Board class.
	 * Otherwise creates the required cell.
	 * */
	public Cell(char r, char c) throws Exception
	{
		if(r<Board.rowMin || r>Board.rowMax || c<Board.colMin || c>Board.colMax)
			throw new Exception("Invalid row and column for a cell");
		row = r;
		col = c;
		
		selected = false;
		nextMove = false;
	}
	
	/**
	 * Sets the highlighted flag of the cell to parameter x.
	 * */
	public void select(boolean x)
	{
		this.selected = x;
	}
	
	/**
	 * Sets the nextMove attribute of the cell equal to the method parameter.
	 * */
	public void setNextMove(boolean x)
	{
		nextMove = x;
	}
	
	/**
	 * @return the selected state of the cell.
	 * */
	public boolean isSelected()
	{
		return selected;
	}
	
	/**
	 * @return true iff this cell is the next move of the selected piece.
	 * */
	public boolean isNextMove()
	{
		return nextMove;
	}

	/**
	 * string representation of the cell.
	 * @return a1 etc. if a is the column and 1 is the row number.
	 * */
	@Override
	public String toString()
	{
		return this.col+""+this.row;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Cell))
			return false;
		Cell other = (Cell)obj;
		if(other.row == this.row && other.col == this.col)
			return true;
		return false;
	} 
}
