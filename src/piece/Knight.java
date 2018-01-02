package piece;

import java.util.ArrayList;

import chess.*;

public class Knight extends Piece {
	
	public Knight(String col, Cell cell)
	{	super(col, cell);
	}
	
	public String toString()
	{
		return colour.charAt(0)+"N";
	}
	
	/* A knight can move only 2 in one dimension and 1 in the other.
	 * i.e. it can move only by 5 in squared Euclidean distance.
	 * */
	private boolean validMove(Cell dest, Board board)
	{
		if(dest == null)
			return false;
		if(dest.getPiece().getColour() != this.colour)
			return true;
		else
			return false;
	}

	//Since knight doesn't move linearly, it can't use movesInDir().
	protected ArrayList<Cell> getAllMoves(Board board) 
	{
		this.moves = new ArrayList<Cell>();
		final char cr= currentPos.row, cc= currentPos.col;
		Cell possibleCells[]= 
		{	
			board.getCellAt((char)(cr-2), (char)(cc-1)),
			board.getCellAt((char)(cr-2), (char)(cc+1)),
			board.getCellAt((char)(cr-1), (char)(cc-2)),
			board.getCellAt((char)(cr-1), (char)(cc+2)),
			board.getCellAt((char)(cr+1), (char)(cc-2)),
			board.getCellAt((char)(cr+1), (char)(cc+2)),
			board.getCellAt((char)(cr+2), (char)(cc-1)),
			board.getCellAt((char)(cr+2), (char)(cc+1)),
		};
		
		for(Cell dest : possibleCells)
		{	if(validMove(dest, board))
			{
				moves.add(dest);
			}
		}
		return moves;
	}
}
