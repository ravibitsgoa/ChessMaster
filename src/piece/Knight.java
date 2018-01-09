package piece;

import java.util.ArrayList;

import chess.*;

public class Knight extends Piece 
{
	public Knight(String col, Cell cell) throws Exception
	{	super(col, cell);
	}
	
	public String toString()
	{
		return colour.charAt(0)+"N";
	}
	
	/* A knight can move only 2 in one dimension and 1 in the other.
	 * i.e. it can move only by 5 in squared Euclidean distance.
	 * */
	private boolean validMove(Cell dest, Board board) throws Exception
	{
		if(board == null)
			throw new Exception("Empty board exception in validMove()");
		
		if(dest == null)
			return false;

		if(board.colourAt(dest) != this.colour)
			return true;
		else
			return false;
	}

	//Since knight doesn't move linearly, it can't use movesInDir().
	protected ArrayList<Cell> getAllMoves(Board board)
	{
		if(board == null)
			return null;
		
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
		{	
			try 
			{
				if(validMove(dest, board))
				{
					moves.add(dest);
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("Exception in getAllMoves() of Knight");
			}
		}
		return moves;
	}
}
