package piece;

import java.util.ArrayList;

import chess.*;

public class Bishop extends Piece {
	
	public Bishop(String col, Cell cell)
	{	super(col, cell);
	}
	
	public String toString()
	{
		return colour.charAt(0)+"B";
	}
	
	/* A bishop can move only diagonally, hence
	 * sum or difference of (row, col) of current and 
	 * destination cell must be the same.
	 */

	protected ArrayList<Cell> getAllMoves(Board board) 
	{
		this.moves = new ArrayList<Cell>();
		
		//If the bishop is to be moved along a constant sum diagonal.
		//final int sum = currentPos.row + currentPos.col;
		moves.addAll(this.movesInDir(board, 1, -1));
		moves.addAll(this.movesInDir(board, -1, 1));
		
		//If the bishop is to be moved along a constant difference diagonal.
		//final int diff = currentPos.row - currentPos.col;
		moves.addAll(this.movesInDir(board, 1, 1));
		moves.addAll(this.movesInDir(board, -1, -1));
		
		return this.moves;
	}
	
}
