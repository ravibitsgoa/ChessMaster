package piece;
import java.util.ArrayList;

import chess.*;

/**
 * @author Ravishankar P. Joshi
 * */
public class Rook extends Piece 
{
	private boolean inInitialState;
	public Rook(String col, Cell cell) throws Exception
	{	super(col, cell);
		inInitialState = true;
	}
	
	/**
	 * Returns the rook as a string.
	 * @return "WR" for a white rook. Returns "BR" otherwise.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"R";
	}
	
	public boolean canCastle()
	{
		return inInitialState;
	}
	
	/** 
	 * A rook can move only in same row/ column.
	 * A piece can move to dest cell only if 
	 * 1. it's either empty or
	 * 2. occupied by a piece of opposite colour.
	 * */
	@Override
	public ArrayList<Cell> getAllMoves(Board board) 
	{
		this.moves = new ArrayList<Cell>();
		
		//If the rook is to be moved along a row.
		moves.addAll(this.movesInDir(board, 1, 0));
		moves.addAll(this.movesInDir(board, -1, 0));
		
		//If the rook is to be moved along a column.
		moves.addAll(this.movesInDir(board, 0, 1));
		moves.addAll(this.movesInDir(board, 0, -1));
		
		if(this.canCastle())
		{
			King king = board.getKing(this.colour);
			if(	king.canKingSideCastle() &&
				this.currentPos.col < king.currentPos.col)
			{
				char row = this.currentPos.row, col= this.currentPos.col;
				moves.add(board.getCellAt(row, col+2));
			}
			else if(king.canQueenSideCastle() &&
					this.currentPos.col > king.currentPos.col)
			{
				char row = this.currentPos.row, col= this.currentPos.col;
				moves.add(board.getCellAt(row, col-3));
			}

		}
		
		return this.moves;
	}
	
	@Override
	public boolean moveTo(Cell dest, Board board)
	{
		boolean moved = super.moveTo(dest, board);
		if(moved)
			inInitialState = false;
		return moved;
	}
	
	/**
	 * Castles the rook by moving it to appropriate cell.
	 * @return true if castling is completed,
	 * false otherwise.
	 * */
	public boolean castle(Board board)
	{
		if(this.canCastle())
		{
			King king = board.getKing(this.colour);
			
			char row = this.currentPos.row, col= this.currentPos.col;
			if(	//king.canQueenSideCastle() &&
				this.currentPos.col < king.currentPos.col)
			{	
				this.currentPos.setPiece(null);
				Cell dest = (board.getCellAt(row, col+2));
				this.currentPos = dest;
				dest.setPiece(this);
				return true;
			}
			else if(//king.canKingSideCastle() &&
					this.currentPos.col > king.currentPos.col)
			{
				this.currentPos.setPiece(null);
				Cell dest = (board.getCellAt(row, col-3));
				this.currentPos = dest;
				dest.setPiece(this);
				return true;
			}
			else
				System.out.println("Exception in castle() of Rook");
		}
		return false;
	}
}
