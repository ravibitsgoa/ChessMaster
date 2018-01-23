package piece;

import java.util.ArrayList;

import chess.*;

/**
 * @author Ravishankar P. Joshi
 * */
public class Bishop extends Piece 
{	
	public Bishop(String col, Cell cell) throws Exception
	{	super(col, cell);
	}
	
	/**
	 * Returns the string representation of the bishop.
	 * @return WB for a white bishop.
	 * BB for a black bishop.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"B";
	}
	
	/** 
	 * A bishop can move only diagonally, hence
	 * sum or difference of (row, col) of current and 
	 * destination cell must be the same.
	 * */
	@Override
	public ArrayList<Cell> getAllMoves(Board board) 
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
