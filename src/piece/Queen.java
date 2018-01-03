package piece;

import java.util.ArrayList;

import chess.Board;
import chess.Cell;

public class Queen extends Piece {
	
	public Queen(String col, Cell cell) throws Exception
	{	super(col, cell);
	}
	
	public String toString()
	{
		return colour.charAt(0)+"Q";
	}
	
	/* A queen can move only where a rook or a bishop can move.
	 * i.e. it can move in same row or column (like a rook)
	 * or it can move in same diagonal (like a bishop)
	 * I.e. it can go to dest if sum(row, column) or diff(row, column) of 
	 * its current cell and dest are same.
	 * */
	protected ArrayList<Cell> getAllMoves(Board board) 
	{
		this.moves = new ArrayList<Cell>();
		
		//If the queen is to be moved along a constant sum diagonal.
		//final int sum = currentPos.row + currentPos.col;
		moves.addAll(this.movesInDir(board, 1, -1));
		moves.addAll(this.movesInDir(board, -1, 1));
		
		//If the queen is to be moved along a constant difference diagonal.
		//final int diff = currentPos.row - currentPos.col;
		moves.addAll(this.movesInDir(board, 1, 1));
		moves.addAll(this.movesInDir(board, -1, -1));
		
		//If the queen is to be moved along a row.
		moves.addAll(this.movesInDir(board, 1, 0));
		moves.addAll(this.movesInDir(board, -1, 0));
		
		//If the queen is to be moved along a column.
		moves.addAll(this.movesInDir(board, 0, 1));
		moves.addAll(this.movesInDir(board, 0, -1));
		
		return this.moves;
	}
	
}
