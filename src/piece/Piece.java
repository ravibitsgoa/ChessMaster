package piece;
import java.util.ArrayList;

import chess.*;

/**
 * @author Ravishankar P. Joshi
 * */
public abstract class Piece 
{
	protected final String colour;
	protected Cell currentPos;
	protected ArrayList<Cell> moves;
	
	/**
	 * Constructor of this abstract class to avoid repetition 
	 * 
	 * @throws null cell exception
	 * @throws cell non-empty exception
	 * @throws null colour exception
	 **/
	public Piece(String col, Cell cell) throws Exception
	{	
		if(cell == null)
			throw new Exception("Cell null exception.");
		
		if(cell.getPiece() != null)
			throw new Exception("Cell not empty exception");
		
		if(col == null)
			throw new Exception("Colour null exception.");
		
		this.colour = col;
		this.currentPos = cell;
		
		cell.setPiece(this);
		this.moves = null;
	}
	
	/**
	 * An abstract method to be over-ridden by child classes.
	 * */
	public abstract String toString();
	
	/** 
	 * Checks whether the Piece can be moved into cell dest or not.
	 * @return True if it can be moved to dest, it moves itself, 
	 * changes occupant Piece of the cells.
	 * (It calls canMoveTo method to decide this.)
	 * Returns false otherwise, without any modifying anything.
	 * Returns false if either of the arguments are null.
	 * */
	public boolean moveTo(Cell dest, Board board)
	{
		if(dest == null || board == null)
			return false;
		if(canMoveTo(dest, board))
		{	
			this.currentPos.setPiece(null);	//empty the current position.
			dest.setPiece(this);			//fill the destination position.
			this.currentPos = dest;			//replace current position with new one.
			this.getAllMoves(board);		//find all moves reachable from here.
			return true;
		}
		else
			return false;
	}
	
	/** 
	 * This method doesn't modify anything.
	 * It just checks whether destination cell is contained in
	 * the list of moves.
	 * 
	 * @return true if this piece can move to cell dest,
	 * Returns false otherwise.
	 * Returns false if either of the arguments are null.
	 * */
	public boolean canMoveTo(Cell dest, Board board)
	{
		if(dest == null || board == null)
			return false;
		
		this.getAllMoves(board);
		//If we don't know all the moves from this position or
		//If this piece is a king, get all the moves.
		//For king, moves can change possibly after each move of
		//a piece on the board.
		
		return this.moves.contains(dest);
	}
	
	/**
	 * An abstract method to be over-ridden by child classes.
	 * 
	 * @return an ArrayList of Cells to which the piece can
	 * move to, from current position.
	 * */
	public abstract ArrayList<Cell> getAllMoves(Board board);
	
	/** 
	 * @return an ArrayList of Cells from current cell, 
	 * in the the direction of the vector (rowDir, colDir),
	 * to which this piece can move.
	 * */
	protected ArrayList<Cell> movesInDir(Board board, int rowDir, int colDir)
	{
		ArrayList<Cell> listOfMoves = new ArrayList<Cell>();
		char row=(char)(currentPos.row+rowDir);
		char col =(char)(currentPos.col + colDir);
		for(; row<=Board.rowMax && col<=Board.colMax &&
			row>=Board.rowMin && col>=Board.colMin; row+=rowDir, col+=colDir)
		{
			if(board.colourAt(row, col) == this.colour)
			{	
				break;
			}
			//if this piece is blocked by one of its own color,
			//it can't move ahead.
			
			if(board.colourAt(row, col) != null)
			{	
				listOfMoves.add(board.getCellAt(row, col));
				break;
			}
			//if this piece is blocked by one of opposite color,
			//it can't move ahead.
			
			listOfMoves.add(board.getCellAt(row, col));
			//If that cell is empty, we can easily go there.
		}
		return listOfMoves;
	}
	
	/**
	 * @return the colour of the piece as a string.
	 * */
	public String getColour() 
	{
		return colour;
	}
	
}
