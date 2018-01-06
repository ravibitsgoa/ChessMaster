package piece;
import java.util.ArrayList;

import chess.*;

public abstract class Piece 
{
	protected final String colour;
	protected Cell currentPos;
	protected ArrayList<Cell> moves;
	
	/* Constructor of this abstract class to avoid repetition 
	 * in child classes, whose constructor does mainly this.
	 */
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
	
	public abstract String toString();
	
	/* Checks whether the Piece can be moved into cell dest or not.
	 * If it can be moved to dest, it moves itself, 
	 * changes occupant Piece of the cells, and returns true.
	 * (It calls canMoveTo method to decide this.)
	 * returns false otherwise, without any modifying anything.
	 * */
	public boolean moveTo(Cell dest, Board board)
	{
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
	
	/* Returns true if this piece can move to cell dest,
	 * Returns false otherwise.
	 * This method doesn't modify anything.
	 * 
	 * It just checks whether destination cell is contained in
	 * the list of moves.
	 * */
	public boolean canMoveTo(Cell dest, Board board)
	{
		if(this.moves == null || this instanceof King)
			this.getAllMoves(board);
		//If we don't know all the moves from this position or
		//if this is a king, get all the moves.
		//For king, moves can change possibly after each move of
		//a piece on the board.
		
		return this.moves.contains(dest);
	}
	
	protected abstract ArrayList<Cell> getAllMoves(Board board);
	
	/* movesInDir method returns a list of moves, by moving
	 * from current cell, to the the direction of vector (rowDir, colDir).
	 */
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
	
	public String getColour() 
	{
		return colour;
	}
	
}
