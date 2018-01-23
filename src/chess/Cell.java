package chess;
import piece.*;

/**
 * @author Ravishankar P. Joshi
 * */
public class Cell 
{
	private Piece piece;
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
		piece = null;
		selected = false;
		nextMove = false;
	}
	
	/**
	 * Sets the highlighted flag of the cell to parameter x.
	 * @return true iff the playerColour is the same as the colour of the 
	 * piece on this cell.
	 * Return false if the cell is empty or contains an opponent cell.
	 * */
	public boolean select(String playerColour, boolean x)
	{
		if(this.getPiece() == null)
			return false;
		if(playerColour == this.getPiece().getColour())
		{	
			selected = x;
			return true;
		}
		else
			return false;
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
	 * @return true if the cell is currently empty or has a piece of
	 * colour opposite to that of the newPiece.
	 * 
	 * Returns true if we attempt to set the same piece on the cell,
	 * as already exists on it.
	 * 
	 * Returns true if we try to empty the cell.
	 * 
	 * Returns false otherwise.
	 * */
	public boolean setPiece(Piece newPiece)
	{
		if(this.piece != null && newPiece != null
			&& newPiece != this.piece
			&& this.piece.getColour() == newPiece.getColour())
		{	
			return false;
		}
		
		this.piece = newPiece;
		return true;
	}
	
	/**
	 * @return piece on this cell.
	 * */
	public Piece getPiece()
	{
		return this.piece;
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
	
	/** Returns true iff the given object is a cell,
	 * and has the same row and column parameters as this one.
	 * */
	@Override
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
