package piece;
import java.util.ArrayList;

import chess.*;

public class Rook extends Piece 
{
	public Rook(String col, Cell cell) throws Exception
	{	super(col, cell);
	}
	
	public String toString()
	{
		return colour.charAt(0)+"R";
	}
	
	/* A rook can move only in same row/ column.
	 * A piece can move to dest cell only if 
	 * 1. it's either empty or
	 * 2. occupied by a piece of opposite color.*/
	
	protected ArrayList<Cell> getAllMoves(Board board) 
	{
		this.moves = new ArrayList<Cell>();
		
		//If the rook is to be moved along a row.
		moves.addAll(this.movesInDir(board, 1, 0));
		moves.addAll(this.movesInDir(board, -1, 0));
		
		//If the rook is to be moved along a column.
		moves.addAll(this.movesInDir(board, 0, 1));
		moves.addAll(this.movesInDir(board, 0, -1));
		
		return this.moves;
	}
	
}
